package com.hundredvisions.jokeapp;


public class IcndbJoke {
    private String type; // use same name as JSON key
    private JokeWrapper value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JokeWrapper getValue() {
        return value;
    }

    public void setValue(JokeWrapper value) {
        this.value = value;
    }

    public class JokeWrapper {
        private int id;
        private String joke;
        private String[] categories;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJoke() {
            return joke;
        }

        public void setJoke(String joke) {
            this.joke = joke;
        }

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }
}
