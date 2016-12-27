package cm.fitnessbud;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.SQLDataException;
import java.util.List;

/**
 * Created by David Sereno on 22/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="FitnessHam.db";
    private static final int SCHEMA=1;

    //SCHEMA Version is 1
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    void resetDSet() {
        SQLiteDatabase db=getWritableDatabase();
        try {
            db.beginTransaction();
            //TO DO
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
        onCreate(db);
    }
    //Criação da Base De Dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table creation !!!
        try {
            db.beginTransaction();
            //db.execSQL("CREATE TABLE IF NOT EXISTS Ingredientes( `idIngredientes` INT NOT NULL AUTO_INCREMENT, `calorias_ingrediente` INT NOT NULL,  `proteinas_ingrediente` INT NOT NULL  `lipidos_ingrediente` INT NOT NULL,  `hidratos_ingrediente` INT NOT NULL,  `nome_ingrediente` VARCHAR(45) NOT NULL,  PRIMARY KEY (`idIngredientes`)) ENGINE = InnoDB;");
            db.execSQL("CREATE TABLE Ingredientes (idIngredientes integer primary key autoincrement," +
                    " nome_ingrediente  TEXT NOT NULL," +
                    "calorias_ingrediente INT not null ," +
                    "hidratos_ingrediente  INT NOT NULL," +
                    "lipidos_ingrediente  INT NOT NULL," +
                    "proteinas_ingrediente INT NOT NULL);");
            Log.d("Hello","Tabela Ingredientes Criada");


            db.execSQL("CREATE TABLE  Receitas (idReceitas integer primary key autoincrement," +
                    "  nome_receita TEXT NOT NULL," +
                    "  doses_receita INT NOT NULL)");
            Log.d("Hello","Tabela Receitas Criada");


            db.execSQL("CREATE TABLE Consumos (idConsumos integer primary key autoincrement," +
                    "  `idReceitas` integer NOT NULL," +
                    "  `data_consumo` DATE NOT NULL," +
                    "  `doses_consumo` INT NOT NULL," +
                    "FOREIGN KEY(idReceitas) REFERENCES Receitas(idReceitas) );");
            Log.d("Hello","Tabela Consumos Criada");


            db.execSQL("CREATE TABLE Receitas_has_Ingredientes(" +
                    "idIngredientes integer not null," +
                    "idReceitas integer not null," +
                    "qnt INT not null Default 1," +
                    "PRIMARY KEY (idIngredientes, idReceitas)" +
                    "FOREIGN KEY(idIngredientes) REFERENCES Ingredientes(idIngredientes)," +
                    "FOREIGN KEY(idReceitas) REFERENCES Receitas(idReceitas) );");
            Log.d("Hello","Tabela Intermedia Criada");

            db.execSQL("CREATE TABLE Configuracoes (" +
                    "  `idConfiguracoes` integer primary key autoincrement," +
                    "  `NOME` TEXT NOT NULL," +
                    "  `IDADE` INT NOT NULL," +
                    "  `SEXO` TEXT CHECK( SEXO IN ('M','F') ) NOT NULL DEFAULT 'M', " +
                    "  `ALTURA` INT NOT NULL," +
                    "  `WEIGHT` INT NOT NULL," +
                    "  `GOAL`  TEXT CHECK(GOAL IN  ('PerderPeso', 'GanharMusculo') ) NOT NULL DEFAULT 'PerderPeso'," +
                    "  `EXERCISE`  TEXT CHECK(EXERCISE IN ('Muito', 'Moderado','Pouco') ) NOT  NULL DEFAULT 'Moderado');");
            Log.d("Hello","Tabela Configurações Criada");

            db.execSQL("INSERT VALUES() INTO");

            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        //TO DO
    }

    /**
     * Aqui temos as funções que permitem a manipulação da Base de Dados
     */

    //Insert into Ingredients
    public void insertIngredient(String name,int calories,int carbon,int protein,int lipids){
        SQLiteDatabase  db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Ingredientes.COLUMN_CALORIES,calories);
        values.put(DatabaseContract.Ingredientes.COLUMN_HIDRATS,carbon);
        values.put(DatabaseContract.Ingredientes.COLUMN_LIPIDS,lipids);
        values.put(DatabaseContract.Ingredientes.COLUMN_PROTEINS,protein);
        values.put(DatabaseContract.Ingredientes.COLUMN_NAME,name);
        long result = db.insert(DatabaseContract.Ingredientes.TABLE_NAME,null,values);
        if(result !=-1){
            db.setTransactionSuccessful();
        }
        else{
            Log.e("INSERTION_ERROR","The Ingredient was not Added");
        }
        db.endTransaction();
    }

    //Insert a new recipe using 2 lists with the ingredients to add and their respoective quantities
    public void insertRecipe(List<Integer> ingredients_index,List<Integer> quantity,String name, int doses){
        SQLiteDatabase  db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Receitas.COLUMN_NAME,name);
        values.put(DatabaseContract.Receitas.COLUMN_DOSES,doses);
        long index = db.insert(DatabaseContract.Receitas.TABLE_NAME,null,values);

        if(ingredients_index.size() != quantity.size()){
            //devia dar erro ou algo do genero
        }

        //for each ingredient in the recipe list we had it to the intermedary table
        values = new ContentValues();
        int size = ingredients_index.size();
        for (int i =0;i<size;i++){
            values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_INGREDIENT,ingredients_index.get(i));
            values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_QUANTITY,quantity.get(i));
            values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_RECIPE,index);
            values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_RECIPE,index);
            db.insert(DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME,null,values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //Insert an empty recipe
    public long insertRecipe(String name,int doses){
        SQLiteDatabase  db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Receitas.COLUMN_NAME,name);
        values.put(DatabaseContract.Receitas.COLUMN_DOSES,doses);
        long index = db.insert(DatabaseContract.Receitas.TABLE_NAME,null,values);

        db.setTransactionSuccessful();
        db.endTransaction();
        return index;
    }

    //Insert an ingredient into an existing recipe
    public long insertIngredientIntoRecipe(int idRecipe,int idIngredient, int quantity){
        SQLiteDatabase  db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_INGREDIENT,idIngredient);
        values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_QUANTITY,quantity);
        values.put(DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_RECIPE,idRecipe);
        long index = db.insert(DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
        return index;
    }
    //Insert a consuption with the prefered date
    public void insertConsuption(int idRecipe, String date,int doses){
        SQLiteDatabase  db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Consumos.COLUMN_RECIPE_ID,idRecipe);
        values.put(DatabaseContract.Consumos.COLUMN_DATE,date);
        values.put(DatabaseContract.Consumos.COLUMN_DOSES,doses);
        db.insert(DatabaseContract.Consumos.TABLE_NAME,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
    //Insert configurations
    public void insertConfigurations(int age, int heigth,String sex,String name,String exercise,String goal,int weigth){
        SQLiteDatabase  db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Configuracoes.COLUMN_AGE,age);
        values.put(DatabaseContract.Configuracoes.COLUMN_SEX,sex);
        values.put(DatabaseContract.Configuracoes.COLUMN_HEIGTH,heigth);
        values.put(DatabaseContract.Configuracoes.COLUMN_NAME,name);
        values.put(DatabaseContract.Configuracoes.COLUMN_EXERCISE,exercise);
        values.put(DatabaseContract.Configuracoes.COLUMN_GOAL,goal);
        values.put(DatabaseContract.Configuracoes.COLUMN_WEIGTH,weigth);
        db.insert(DatabaseContract.Configuracoes.TABLE_NAME,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void updateConfigurations(int age, int heigth,String sex,String name,String exercise,String goal,String weigth){
        SQLiteDatabase  db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Configuracoes.COLUMN_AGE,age);
        values.put(DatabaseContract.Configuracoes.COLUMN_SEX,sex);
        values.put(DatabaseContract.Configuracoes.COLUMN_HEIGTH,heigth);
        values.put(DatabaseContract.Configuracoes.COLUMN_NAME,name);
        values.put(DatabaseContract.Configuracoes.COLUMN_EXERCISE,exercise);
        values.put(DatabaseContract.Configuracoes.COLUMN_GOAL,goal);
        values.put(DatabaseContract.Configuracoes.COLUMN_WEIGTH,weigth);
        //VERIFICAR ISTO
        db.update(DatabaseContract.Configuracoes.TABLE_NAME,values,"WHERE idConfiguracoes = 1",null);
    }
    public void updateIngredient(String name,int calories,int carbon,int protein,int lipids){
        SQLiteDatabase  db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Ingredientes.COLUMN_CALORIES,calories);
        values.put(DatabaseContract.Ingredientes.COLUMN_HIDRATS,carbon);
        values.put(DatabaseContract.Ingredientes.COLUMN_LIPIDS,lipids);
        values.put(DatabaseContract.Ingredientes.COLUMN_PROTEINS,protein);
        values.put(DatabaseContract.Ingredientes.COLUMN_NAME,name);
        //VERIFICAR ISTO
        db.update(DatabaseContract.Ingredientes.TABLE_NAME,values,"WHERE "+DatabaseContract.Ingredientes.COLUMN_ID+" = 1",null);
    }

    public void removeIngredientFromRecipe(int idIngredient, int idRecipe){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME,
                DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_INGREDIENT + "=" + idIngredient
                        + " AND "+DatabaseContract.Receitas_has_Ingredientes.COLUMN_ID_RECIPE+"="+idRecipe,
                null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteRecipe(int idRecipe){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(DatabaseContract.Receitas.TABLE_NAME,
                DatabaseContract.Receitas.COLUMN_ID + "=" + idRecipe,
                null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteIngredient(int idIngredient){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(DatabaseContract.Ingredientes.TABLE_NAME,
                DatabaseContract.Ingredientes.COLUMN_ID + "=" + idIngredient,
                null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deleteConsuption(int idConsuption){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(DatabaseContract.Consumos.TABLE_NAME,
                DatabaseContract.Consumos.COLUMN_ID + "=" + idConsuption,
                null);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor getRecipeList(){
        //SELECT idReceitas,nome_receita,SUM(calorias_ingrediente*qnt) as calorias FROM receitas INNER JOIN receitas_has_ingredientes USING(idReceitas) INNER JOIN ingredientes USING(idIngredientes) GROUP by idReceitas
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT idReceitas as _id,nome_receita,SUM(calorias_ingrediente*qnt) as calorias" +
                " FROM "+DatabaseContract.Receitas.TABLE_NAME+"" +
                " INNER JOIN "+DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME+" USING(idReceitas) " +
                "INNER JOIN "+DatabaseContract.Ingredientes.TABLE_NAME+" USING(idIngredientes) " +
                "GROUP by idReceitas",null,null);
    }

    public Cursor getRecipe(int recipeId){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT nome_receita,SUM(calorias_ingrediente*qnt) as calorias,SUM(proteinas_ingrediente*qnt) as proteinas,SUM(lipidos_ingrediente*qnt) as lipidos, SUM(hidratos_ingrediente*qnt) as hidratos" +
                " FROM "+DatabaseContract.Receitas.TABLE_NAME+"" +
                " INNER JOIN "+DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Ingredientes.TABLE_NAME+" USING(idIngredientes) " +
                " WHERE idReceitas ="+recipeId+
                " GROUP by idReceitas",null,null);
    }
    public Cursor getRecipeIngredients(int recipeId){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT idIngredientes as _id,nome_ingrediente,qnt,calorias_ingrediente" +
                " FROM "+DatabaseContract.Receitas.TABLE_NAME+"" +
                " INNER JOIN "+DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Ingredientes.TABLE_NAME+" USING(idIngredientes) " +
                " WHERE idReceitas ="+recipeId+
                " ",null,null);
    }

    public Cursor getIngredientList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DatabaseContract.Ingredientes.TABLE_NAME,null,null,null,null,null,null);
    }
    public Cursor getIngredient(int idIngredient){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+DatabaseContract.Ingredientes.TABLE_NAME+" WHERE "+DatabaseContract.Ingredientes.COLUMN_ID+"="+idIngredient,null);
    }

    public Cursor getConfigurations(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DatabaseContract.Configuracoes.TABLE_NAME,null,null,null,null,null,null);
    }

    public Cursor getConsuptions(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT nome_receita,doses_consumo,SUM(calorias_ingrediente*qnt)*doses_consumo as calorias" +
                " FROM "+DatabaseContract.Consumos.TABLE_NAME+"" +
                " INNER JOIN "+DatabaseContract.Receitas.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Ingredientes.TABLE_NAME+" USING(idIngredientes) " +
                "GROUP by idConsumos",null,null);
    }

    /**
     * This method gets the user the amout of calories,carbon hidrats,lipids and proteins in the requested date
     *
     * @param  date  the day of the consuptions
     * @return      a Cursor with the name(String),doses(INT),calories(String),proteins(String),lipids(String),carbon(String)
     */
    public Cursor getDailyResults(String date){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT nome_receita,doses_consumo,SUM(calorias_ingrediente*qnt)*doses_consumo as calorias,SUM(proteinas_ingrediente*qnt)*doses_consumo as proteinas,SUM(lipidos_ingrediente*qnt)*doses_consumo as lipidos,SUM(hidratos_ingrediente*qnt)*doses_consumo as hidratos" +
                " FROM "+DatabaseContract.Consumos.TABLE_NAME+"" +
                " INNER JOIN "+DatabaseContract.Receitas.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Receitas_has_Ingredientes.TABLE_NAME+" USING(idReceitas) " +
                " INNER JOIN "+DatabaseContract.Ingredientes.TABLE_NAME+" USING(idIngredientes) " +
                " WHERE "+DatabaseContract.Consumos.COLUMN_DATE+"= '"+date +
                "' GROUP by idConsumos",null,null);
    }

}