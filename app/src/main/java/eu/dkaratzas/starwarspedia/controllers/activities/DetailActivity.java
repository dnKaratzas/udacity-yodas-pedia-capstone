package eu.dkaratzas.starwarspedia.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.dkaratzas.starwarspedia.R;
import eu.dkaratzas.starwarspedia.adapters.RelatedToAdapter;
import eu.dkaratzas.starwarspedia.api.StarWarsApiCallback;
import eu.dkaratzas.starwarspedia.libs.GlideApp;
import eu.dkaratzas.starwarspedia.libs.Misc;
import eu.dkaratzas.starwarspedia.libs.SpacingItemDecoration;
import eu.dkaratzas.starwarspedia.models.SwapiModel;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {
    private static final int LOADER_ID = 90;

    private SwapiModel mSwapiModel;
    public static final String EXTRA_DATA_TO_DISPLAY = "extra_data";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ivThumb)
    ImageView mIvThumb;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvDetails)
    TextView mTvDetails;
    @BindView(R.id.bottomContainer)
    LinearLayout mBottomContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(EXTRA_DATA_TO_DISPLAY)) {
            mSwapiModel = bundle.getParcelable(EXTRA_DATA_TO_DISPLAY);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            publishUI();

        } else {
            throw new IllegalArgumentException("Must provide a SwapiModel as intent extra to display it's data!");
        }
    }

    private void publishUI() {
        getSupportActionBar().setTitle(mSwapiModel.getCategory().getString(getApplicationContext()));
        mTvTitle.setText(mSwapiModel.getTitle());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(6));
        GlideApp.with(this)
                .load(mSwapiModel.getImageStorageReference())
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mIvThumb);

        Map<String, String> detailsMap = mSwapiModel.getDetailsToDisplay(getApplicationContext());
        StringBuilder details = new StringBuilder();
        for (Map.Entry<String, String> entry : detailsMap.entrySet()) {
            details.append(String.format("<b>%s:</b> %s<br>", entry.getKey(), entry.getValue()));
        }
        mTvDetails.setText(Html.fromHtml(details.toString()));

        loadAndPublishRelatedToRecyclers();

    }

    private void loadAndPublishRelatedToRecyclers() {
        mSwapiModel.getRelatedToItemsAsyncLoader(this, getSupportLoaderManager(), LOADER_ID, new StarWarsApiCallback<Map<String, List<SwapiModel>>>() {
            @Override
            public void onResponse(Map<String, List<SwapiModel>> result) {

                for (Map.Entry<String, List<SwapiModel>> entry : result.entrySet()) {
                    Timber.d("Publishing recycler for %s entry", entry.getKey());

                    TextView title = new TextView(DetailActivity.this);
                    title.setText(entry.getKey());
                    // TODO: Add text size on dimens and margin
                    title.setTextSize(22);
                    int viewsMargin = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.margin_large);
                    title.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    lp.setMargins(0, Misc.dpToPx(24), 0, 0); // first item need extra top margin
                    title.setLayoutParams(lp);

                    RecyclerView recyclerView = new RecyclerView(DetailActivity.this);
                    RelatedToAdapter relatedToAdapter = new RelatedToAdapter(getApplicationContext(), entry.getValue(), new RelatedToAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(SwapiModel swapiModel) {
                            if (swapiModel != null) {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(DetailActivity.EXTRA_DATA_TO_DISPLAY, swapiModel);
                                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                                intent.putExtras(bundle);

                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    SpacingItemDecoration itemDecoration = new SpacingItemDecoration(viewsMargin);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(relatedToAdapter);

                    mBottomContainer.addView(title);
                    mBottomContainer.addView(recyclerView);
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getSupportLoaderManager().destroyLoader(LOADER_ID);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getSupportLoaderManager().destroyLoader(LOADER_ID);
        super.onBackPressed();
    }
}
