package cm.fitnessbud;

import android.provider.BaseColumns;

/**
 * Created by ASUS on 22/12/2016.
 */
public class DatabaseContract {

    private DatabaseContract(){
    }
    public static class Ingredientes implements BaseColumns {
        public static final String TABLE_NAME = "Ingredientes";
        public static final String COLUMN_ID = "idIngredientes";
        public static final String COLUMN_CALORIES = "calorias_ingrediente";
        public static final String COLUMN_NAME = "nome_ingrediente";
        public static final String COLUMN_PROTEINS = "proteinas_ingrediente";
        public static final String COLUMN_HIDRATS = "lipidos_ingrediente";
        public static final String COLUMN_LIPIDS = "hidratos_ingrediente";
    }
    public static class Receitas_has_Ingredientes implements BaseColumns {
        public static final String TABLE_NAME = "Receitas_has_Ingredientes";
        public static final String COLUMN_ID_RECIPE = "idReceitas";
        public static final String COLUMN_ID_INGREDIENT = "idIngredientes";
        public static final String COLUMN_QUANTITY = "qnt";
    }
    public static class Receitas implements BaseColumns {
        public static final String TABLE_NAME = "Receitas";
        public static final String COLUMN_ID = "idReceitas";
        public static final String COLUMN_NAME = "nome_receita";
        public static final String COLUMN_DOSES = "doses_receita";

    }
    public static class Consumos implements BaseColumns {
        public static final String TABLE_NAME = "Consumos";
        public static final String COLUMN_ID = "idConsumos";
        public static final String COLUMN_RECIPE_ID = "idReceitas";
        public static final String COLUMN_DATE = "data_consumo";
        public static final String COLUMN_DOSES = "doses_consumo";
    }
    public static class Configuracoes implements BaseColumns {
        public static final String TABLE_NAME = "Configuracoes";
        public static final String COLUMN_NAME = "NOME";
        public static final String COLUMN_AGE = "IDADE";
        public static final String COLUMN_SEX = "SEXO";
        public static final String COLUMN_HEIGTH = "ALTURA";
        public static final String COLUMN_EXERCISE = "EXERCISE";//ENUM 'Muito', 'Moderado','Pouco'
        public static final String COLUMN_GOAL = "GOAL";//ENUM 'PerderPeso', 'GanharMusculo'
        public static final String COLUMN_WEIGTH = "WEIGHT";
    }
}
