package eu.dkaratzas.starwarspedia.models;

/**
 * Created by JacksonGenerator on 4/12/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import eu.dkaratzas.starwarspedia.api.SwapiCategory;


public class Vehicle extends SwapiModel {
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;
    @JsonProperty("films")
    private List<String> films;
    @JsonProperty("passengers")
    private String passengers;
    @JsonProperty("pilots")
    private List<String> pilots;
    @JsonProperty("edited")
    private String edited;
    @JsonProperty("consumables")
    private String consumables;
    @JsonProperty("created")
    private String created;
    @JsonProperty("length")
    private String length;
    @JsonProperty("url")
    private String url;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("crew")
    private String crew;
    @JsonProperty("vehicle_class")
    private String vehicleClass;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    @JsonProperty("name")
    private String name;
    @JsonProperty("model")
    private String model;

    public Vehicle() {
        super();
        this.maxAtmospheringSpeed = "";
        this.cargoCapacity = "";
        this.films = new ArrayList<>();
        this.passengers = "";
        this.pilots = new ArrayList<>();
        this.edited = "";
        this.consumables = "";
        this.created = "";
        this.length = "";
        this.url = "";
        this.manufacturer = "";
        this.crew = "";
        this.vehicleClass = "";
        this.costInCredits = "";
        this.name = "";
        this.model = "";
    }

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.maxAtmospheringSpeed);
        dest.writeString(this.cargoCapacity);
        dest.writeStringList(this.films);
        dest.writeString(this.passengers);
        dest.writeStringList(this.pilots);
        dest.writeString(this.edited);
        dest.writeString(this.consumables);
        dest.writeString(this.created);
        dest.writeString(this.length);
        dest.writeString(this.url);
        dest.writeString(this.manufacturer);
        dest.writeString(this.crew);
        dest.writeString(this.vehicleClass);
        dest.writeString(this.costInCredits);
        dest.writeString(this.name);
        dest.writeString(this.model);
    }

    protected Vehicle(Parcel in) {
        this.maxAtmospheringSpeed = in.readString();
        this.cargoCapacity = in.readString();
        this.films = in.createStringArrayList();
        this.passengers = in.readString();
        this.pilots = in.createStringArrayList();
        this.edited = in.readString();
        this.consumables = in.readString();
        this.created = in.readString();
        this.length = in.readString();
        this.url = in.readString();
        this.manufacturer = in.readString();
        this.crew = in.readString();
        this.vehicleClass = in.readString();
        this.costInCredits = in.readString();
        this.name = in.readString();
        this.model = in.readString();
    }

    public static final Parcelable.Creator<Vehicle> CREATOR = new Parcelable.Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel source) {
            return new Vehicle(source);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
    //endregion

    //region Getters
    public String getMaxAtmospheringSpeed() {
        return maxAtmospheringSpeed;
    }

    public String getCargoCapacity() {
        return cargoCapacity;
    }

    public List<String> getFilms() {
        return films;
    }

    public String getPassengers() {
        return passengers;
    }

    public List<String> getPilots() {
        return pilots;
    }

    public String getEdited() {
        return edited;
    }

    public String getConsumables() {
        return consumables;
    }

    public String getCreated() {
        return created;
    }

    public String getLength() {
        return length;
    }

    public String getUrl() {
        return url;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getCrew() {
        return crew;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public String getCostInCredits() {
        return costInCredits;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    @Override
    public int getId() {
        return getIdFromUrl(url);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public SwapiCategory getCategory() {
        return SwapiCategory.VEHICLE;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "maxAtmospheringSpeed='" + maxAtmospheringSpeed + '\'' +
                ", cargoCapacity='" + cargoCapacity + '\'' +
                ", films=" + films +
                ", passengers='" + passengers + '\'' +
                ", pilots=" + pilots +
                ", edited='" + edited + '\'' +
                ", consumables='" + consumables + '\'' +
                ", created='" + created + '\'' +
                ", length='" + length + '\'' +
                ", url='" + url + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", crew='" + crew + '\'' +
                ", vehicleClass='" + vehicleClass + '\'' +
                ", costInCredits='" + costInCredits + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    //endregion
}