package ase.dam.autoquiz.data_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ase.dam.autoquiz.data_sql.QuizContract.*;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private static QuizDbHelper instance;
    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategorieTable.TABLE_NAME + "( " +
                CategorieTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategorieTable.COLUMN_DENUMIRE + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_CATEGORIE_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORIE_ID + ") REFERENCES " +
                CategorieTable.TABLE_NAME + "(" + CategorieTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategorieTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }
    private void fillCategoriesTable() {
        Categorie c1 = new Categorie("USOR");
        insertCategory(c1);
        Categorie c2 = new Categorie("MEDIU");
        insertCategory(c2);
        Categorie c3 = new Categorie("GREU");
        insertCategory(c3);

    }

    public void addCategory(Categorie categorie)
    {
        db=getWritableDatabase();
        insertCategory(categorie);
    }
    private void insertCategory(Categorie categorie) {
        ContentValues cv = new ContentValues();
        cv.put(CategorieTable.COLUMN_DENUMIRE, categorie.getDenumire());
        db.insert(CategorieTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Fumul de culoare albastra emis de esapament indica:",
                "A) un consum exagerat de ulei", "B)folosirea unei benzine proaste",
                "C)folosirea unei benzine colorate ", 1,
                 Categorie.MEDIU);
        insertQuestion(q1);
        Question q2 = new Question("Viteza se reduce obligatoriu:",
                "A)la trecerile de pietoni", "B)la semnalul politistului de frontiera",
                "C)cand vezi un prieten", 2,
                 Categorie.USOR);
        insertQuestion(q2);
        Question q3 = new Question("In care din situatii e permisa depasirea:",
                "A)cand vreau eu", "B)in cursele de masini",
                "C)in intersectia dirijata prin semnale ale politistului", 3,
               Categorie.USOR);
        insertQuestion(q3);
        Question q4 = new Question("In care din situatii sunteti obligat sa reduceti viteza?",
                "A)la trecerea la nivel cu cale ferata cu bariere",
                "B)la trecerea la nivel cu cale ferata fara bariere",
                "C)la trecerea la nivel cu cale ferata industriala", 1,
                Categorie.USOR);
        insertQuestion(q4);
    }

    public void addQuestion(Question question)
    {
        db=getWritableDatabase();
        insertQuestion(question);
    }
    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_CATEGORIE_ID, question.getCategorie_id());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    //toate categ in list
    public List<Categorie> getCategorii() {
        List<Categorie> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategorieTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Categorie category = new Categorie();
                category.setId(c.getInt(c.getColumnIndex(CategorieTable._ID)));
                category.setDenumire(c.getString(c.getColumnIndex(CategorieTable.COLUMN_DENUMIRE)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }
    //toate intrebarile in list
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategorie_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORIE_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
    public ArrayList<Question> getIntrebariDupaCategorie(int id,String cat) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{cat};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_CATEGORIE_ID + " =?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategorie_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORIE_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public boolean updateQuestion(int id, String q_text, String op1, String op2,String op3,int ans,int id_cat) {
        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION,q_text);
        contentValues.put(QuestionsTable.COLUMN_OPTION1,op1);
        contentValues.put(QuestionsTable.COLUMN_OPTION2,op2);
        contentValues.put(QuestionsTable.COLUMN_OPTION3,op3);
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NR,ans);
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NR,id_cat);
        db.update(QuestionsTable.TABLE_NAME, contentValues, "_ID = ?",new String[] {Integer.toString(id)});
        return true;
    }


    public boolean deleteQuestion(int id) {
        db = getWritableDatabase();
        return (db.delete(QuestionsTable.TABLE_NAME, "_ID = ?",new String[] {Integer.toString(id)})>0);

    }

    public List<Question> getAllQuestionsSelect1() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME+" WHERE "+QuestionsTable.COLUMN_CATEGORIE_ID+" = 1 ", null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategorie_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORIE_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public List<Question> getAllQuestionsSelect2() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME+" WHERE "+QuestionsTable.COLUMN_ANSWER_NR+" = 1 ", null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategorie_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORIE_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    /*public Cursor viewData()
    {
        db = getReadableDatabase();
        String query="SELECT * FROM "+QuestionsTable.TABLE_NAME;
        Cursor c = db.rawQuery(query,null);
        return c;
    }*/

    public int getNumbers(int id)
    {
        int count=0;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME+" WHERE "+QuestionsTable.COLUMN_CATEGORIE_ID+" =  "+id, null);
        if(c.moveToFirst())
        {
            count= c.getCount();
        }
        c.close();
        return count;
    }
}