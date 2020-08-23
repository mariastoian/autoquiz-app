package ase.dam.autoquiz.data_sql;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {
    }
    public static class CategorieTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_categorie";
        public static final String COLUMN_DENUMIRE = "denumire";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_CATEGORIE_ID = "categorie_id";
    }
}




