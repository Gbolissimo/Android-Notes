package com.gbolissimo.androidnotes.Adapter;



        import android.widget.Filter;


        import com.gbolissimo.androidnotes.model.Notes;


        import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class CustomFilter extends Filter{

    NoteAdapter note_adapter;
    ArrayList<Notes> filterList;


    public CustomFilter(ArrayList<Notes> filterList,NoteAdapter adapter)
    {
        this.note_adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Notes> filteredFireNotes=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getContent().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredFireNotes.add(filterList.get(i));
                }
            }

            results.count=filteredFireNotes.size();
            results.values=filteredFireNotes;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        note_adapter.aFireNotes= (ArrayList<Notes>) results.values;

        //REFRESH
        note_adapter.notifyDataSetChanged();
    }
}