package ucl.ac.uk.model;

import java.util.ArrayList;

public class Item {
    private String name;
    private String index;
    private String description;
    private String URL;
    private String image;
    private String imageURL;

    private Item() {}

    public static class Builder {

        private final String name;
        private String index;
        private String description;
        private String URL;
        private String image;
        private String imageURL;

        public Builder(String name) {
            this.name = name;
        }

        public Builder withIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withURL(String URL) {
            this.URL = URL;
            return this;
        }

        public Builder withImageName(String image) {
            this.image = image;
            return this;
        }

        public Builder withImageURL(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.name = this.name;
            item.index = this.index;
            item.description = this.description;
            item.URL = this.URL;
            item.image = this.image;
            item.imageURL = this.imageURL;

            return item;
        }
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getURL() {
        return URL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getItemNames() {
        ArrayList<String> itemNames = new ArrayList<>();
        itemNames.add("Name");
        itemNames.add("Index");
        itemNames.add("Description");
        itemNames.add("URL");
        itemNames.add("Image");
        itemNames.add("ImageURL");
        return itemNames;
    }
}