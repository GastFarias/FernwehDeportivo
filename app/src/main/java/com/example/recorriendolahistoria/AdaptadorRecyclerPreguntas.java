package com.example.recorriendolahistoria;

import static com.example.recorriendolahistoria.R.drawable.__removebg_preview__2_;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recorriendolahistoria.entidades.Preguntas;

import java.util.ArrayList;

public class AdaptadorRecyclerPreguntas
        extends RecyclerView.Adapter<AdaptadorRecyclerPreguntas.ViewHolderPreguntas> implements View.OnClickListener{

    ArrayList<Preguntas> listPreguntas;
    private View.OnClickListener listener;

    public AdaptadorRecyclerPreguntas(ArrayList<Preguntas> listPreguntas){
        this.listPreguntas = listPreguntas;
    }

    @NonNull
    @Override
    public ViewHolderPreguntas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preguntas,null,false);
        view.setOnClickListener(this);
        return new ViewHolderPreguntas(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderPreguntas holder, int position) {
        String Spuntos = "";
        int valor1estrella = 10;
        int valor2estrella = 100;
        int valor3estrella = 1000;

        //If para si no hay puntos se muestra el nombre de la guia
        if (listPreguntas.get(position).getPuntos() == 0){
            switch (listPreguntas.get(position).getGuia()){
                case 3:
                    Spuntos = "Guia : Valentina Gullo";
                    break;
                case 2:
                    Spuntos = "Guia : Brunella Bossi";
                    break;
                case 1:
                    Spuntos = "Guia : Eugenia Navarro";
                    break;
            }
        }else{
            Spuntos = "Puntos Obtenidos: "+listPreguntas.get(position).getPuntos().toString();

        }
        holder.puntos.setText(Spuntos);

       //Pinta la pregunta del color de la guia
        switch (listPreguntas.get(position).getGuia()){
            case 1:
               // holder.nroPreg.setBackgroundColor(R.color.purple_200);
                //holder.puntos.setBackgroundColor(R.color.purple_200);
                holder.layoutVert.setBackgroundResource(R.color.purple_200);
                holder.nroPreg.setTextColor(Color.BLACK);
                holder.puntos.setTextColor(Color.BLACK);
                break;
            case 2:
                holder.layoutVert.setBackgroundResource(R.color.purple_500);
                holder.nroPreg.setTextColor(Color.WHITE);
                holder.puntos.setTextColor(Color.WHITE);

                break;
            case 3:

//                holder.nroPreg.setBackgroundResource(R.color.purple_700);
//                holder.puntos.setBackgroundResource(R.color.purple_700);
                holder.layoutVert.setBackgroundResource(R.color.purple_700);
                holder.nroPreg.setTextColor(Color.WHITE);
                holder.puntos.setTextColor(Color.WHITE);
                break;
        }
        String SnroPreg = "Pregunta Nro: "+ listPreguntas.get(position).getId().toString();
        holder.nroPreg.setText(SnroPreg);

        //dar imagen del resultado

       if (listPreguntas.get(position).getPuntos()== 0){
           switch (listPreguntas.get(position).getGuia()){
               case 1:
                   holder.imRes.setBackgroundResource(R.color.purple_200);
                   break;
               case 2:
                   holder.imRes.setBackgroundResource(R.color.purple_500);
                   break;
               case 3:
                   holder.imRes.setBackgroundResource(R.color.purple_700);
                   break;
           }
        }


        if (listPreguntas.get(position).getPuntos() > 0 && listPreguntas.get(position).getPuntos() < valor1estrella){
           // holder.imRes.setImageAlpha(1);
            holder.imRes.setImageResource(R.drawable.pngwing_com__1_);
            holder.imRes.setBackgroundResource(R.color.rojo);
        }

        if (listPreguntas.get(position).getPuntos()>= valor1estrella && listPreguntas.get(position).getPuntos() <valor2estrella ){
            //holder.imRes.setImageAlpha(1);
            holder.imRes.setImageResource(R.drawable._uno_removebg_preview__2_);
            holder.imRes.setBackgroundResource(R.color.verde);
        }

        if (listPreguntas.get(position).getPuntos()>= valor2estrella && listPreguntas.get(position).getPuntos() <valor3estrella ){
            //holder.imRes.setImageAlpha(1);
            holder.imRes.setImageResource(R.drawable._dos_removebg_preview__2_);
            holder.imRes.setBackgroundResource(R.color.verde);
        }
        if (listPreguntas.get(position).getPuntos()>= valor3estrella){
            //holder.imRes.setImageAlpha(1);
            holder.imRes.setImageResource(R.drawable._tres_removebg_preview__2_);
            holder.imRes.setBackgroundResource(R.color.verde);
        }
    }

    @Override
    public int getItemCount() { return listPreguntas.size(); }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderPreguntas extends RecyclerView.ViewHolder {
        TextView nroPreg, puntos;
        //LinearLayout layout;
        LinearLayout layoutVert;
        ImageView imRes;
        public ViewHolderPreguntas(@NonNull View itemView) {
            super(itemView);
            nroPreg = (TextView) itemView.findViewById(R.id.tv_preguntaRe);
            puntos = (TextView) itemView.findViewById(R.id.tv_puntajeRe);
           // layout = (LinearLayout) itemView.findViewById(R.id.LinearRec);
            imRes = (ImageView) itemView.findViewById(R.id.imResultadoRe);
            layoutVert = (LinearLayout) itemView.findViewById((R.id.layoutVrec));
        }
    }
}
