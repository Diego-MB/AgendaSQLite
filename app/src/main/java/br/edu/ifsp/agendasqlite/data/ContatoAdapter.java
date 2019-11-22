package br.edu.ifsp.agendasqlite.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.agendasqlite.R;
import br.edu.ifsp.agendasqlite.model.Contato;

public class ContatoAdapter
        extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>
        implements Filterable  {

    static List<Contato> contatos;
    List<Contato> contactListFiltered;

    private static ItemClickListener clickListener;


    public void adicionaContatoAdapter(Contato c)
    {
        contatos.add(0,c);

        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato o1, Contato o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        });

        notifyDataSetChanged();

    }

    public void atualizaContatoAdapter(Contato c)
    {


        contatos.set(contatos.indexOf(c),c);
        notifyItemChanged(contatos.indexOf(c));


    }

    public void apagaContatoAdapter(Contato c)
    {
        int pos = contatos.indexOf(c);
        contatos.remove(pos);
        notifyItemRemoved(pos);


    }

    public List<Contato> getContactListFiltered()
    {
        return contactListFiltered;
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
        clickListener = itemClickListener;

    }

    public ContatoAdapter(List<Contato> contatos)
    {
        this.contatos = contatos;
        contactListFiltered=contatos;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.contato_celula,parent,false);

        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
            holder.nome.setText(contactListFiltered.get(position).getNome());
            holder.favorito.setActivated(contactListFiltered.get(position).getFavorito());
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contatos;
                } else {
                    List<Contato> filteredList = new ArrayList<>();
                    for (Contato row : contatos) {
                        if (row.getNome().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListFiltered = (ArrayList<Contato>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class ContatoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        final TextView nome;
        final ImageButton favorito;

        public ContatoViewHolder(@NonNull final View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            favorito = (ImageButton) itemView.findViewById(R.id.favorito);

            favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Contato contato = contactListFiltered.get(getAdapterPosition());
                    ContatoDAO dao = new ContatoDAO(itemView.getContext());

                    contato.setFavorito(!favorito.isActivated());
                    dao.favoritarContato(contato);
                    contactListFiltered.set(contactListFiltered.indexOf(contato), contato);


                    if(!favorito.isActivated()){

                        Toast.makeText(itemView.getContext(), "Favoritado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(itemView.getContext(), "Desfavoritado!", Toast.LENGTH_SHORT).show();
                    }

                    notifyItemChanged(contactListFiltered.indexOf(contato));

                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
              if (clickListener!=null)
                  clickListener.onItemClick(getAdapterPosition());
        }


    }


    public  interface ItemClickListener
    {
        void onItemClick(int position);
    }


}
