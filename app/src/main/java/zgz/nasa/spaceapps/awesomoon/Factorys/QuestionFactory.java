package zgz.nasa.spaceapps.awesomoon.Factorys;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zgz.nasa.spaceapps.awesomoon.DbAdapter;
import zgz.nasa.spaceapps.awesomoon.Tipes.Question;

/**
 * Created by novales35 on 24/04/16.
 */
public class QuestionFactory {
        private DbAdapter db;
        private int MAX;
        public QuestionFactory(DbAdapter db){
            this.db=db;
            if(!db.isOpen())
                db.open();
            Cursor c = db.getCountQuestions();
            c.moveToFirst();
            this.MAX=c.getInt(0);
        }
        public Question getRandomQuestion(){
            Random rnd = new Random();
            int n = 1 + rnd.nextInt(MAX);
            if(!db.isOpen())
                db.open();
            return extractSongs(db.getQuestion(n)).get(0);
        }
        private List<Question> extractSongs(Cursor mCursor){
            List<Question> lista = new ArrayList<Question>();
            if (mCursor.moveToFirst()) {
                do {
                    String texto = mCursor.getString(mCursor.getColumnIndex(DbAdapter.Texto));
                    String correcta = mCursor.getString(mCursor.getColumnIndex(DbAdapter.Correcta));
                    String e1 = mCursor.getString(mCursor.getColumnIndex(DbAdapter.Error1));
                    String e2 = mCursor.getString(mCursor.getColumnIndex(DbAdapter.Error2));
                    String e3 = mCursor.getString(mCursor.getColumnIndex(DbAdapter.Error3));
                    lista.add(new Question(texto,correcta,e1,e2,e3));
                   } while (mCursor.moveToNext());
            }
            //Terminamos de usar el cursor
            mCursor.close();
            return (List) lista;
        }
        public List<Question> getAllQuestions(){
            if(!db.isOpen())
                db.open();
            return extractSongs(db.getAllQuestions());
        }

}
