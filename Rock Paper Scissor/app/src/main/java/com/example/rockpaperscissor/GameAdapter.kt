package com.example.rockpaperscissor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.game_view_cell.view.*

class GameAdapter (private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_view_cell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    private fun onItemClick(portal: Game) {
        println("$portal.result clicked!")
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(game: Game){
            itemView.lblResults.text = game.result
            itemView.imgResultComputer.setImageDrawable(getDrawable(itemView.context, game.computerChoice.drawableId))
            itemView.imgResultHuman.setImageDrawable(getDrawable(itemView.context, game.humanChoice.drawableId))
            itemView.lblTimePlayed.text = game.date.toLocaleString()
        }

        init {
            itemView.setOnClickListener{onItemClick(games[adapterPosition])}
        }
    }


}