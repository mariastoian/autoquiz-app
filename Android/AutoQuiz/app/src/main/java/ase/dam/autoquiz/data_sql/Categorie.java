package ase.dam.autoquiz.data_sql;

import androidx.annotation.NonNull;

public class Categorie {

        public static final int USOR = 1;
        public static final int MEDIU = 2;
        public static final int GREU = 3;

        private int id;
        private String denumire;

        public Categorie() {
        }

        public Categorie(String denumire) {
            this.denumire = denumire;
        }

    @NonNull
    @Override
    public String toString() {
            return getDenumire();    }

    public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getDenumire() {
            return denumire;
        }
        public void setDenumire(String denumire) {
            this.denumire = denumire;
        }

    public static String[] getCategories() {
        return new String[]{
                "USOR",
                "MEDIU",
                "GREU"
        };
    }

}
