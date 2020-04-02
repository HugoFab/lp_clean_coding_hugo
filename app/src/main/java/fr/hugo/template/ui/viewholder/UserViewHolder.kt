package fr.hugo.template.ui.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.hugo.template.R
import fr.hugo.template.data.model.User
import kotlinx.android.synthetic.main.user_list_view_holder.view.*

typealias OnUserClickListener = (view: View, character: User) -> Unit

typealias OnUserLongClickListener = (view: View, character: User) -> Unit

class UserViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: User, onClick: OnUserClickListener, onLongClick : OnUserLongClickListener) {
        itemView.apply {

            user_name.text = model.login
            this.setOnClickListener { onClick(it, model) }

        }
    }

    companion object {
        /**
         * Create a new Instance of [CharacterViewHolder]
         */
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.user_list_view_holder,
                parent,
                false
            )
            return UserViewHolder(view)
        }
    }

}