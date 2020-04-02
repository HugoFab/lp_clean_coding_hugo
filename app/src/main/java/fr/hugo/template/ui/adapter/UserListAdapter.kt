package fr.hugo.template.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import fr.hugo.template.data.model.User
import fr.hugo.template.ui.viewholder.OnUserClickListener
import fr.hugo.template.ui.viewholder.OnUserLongClickListener
import fr.hugo.template.ui.viewholder.UserViewHolder

class UserListAdapter(private var onUserClickListener: OnUserClickListener, var onUserLongClickListener: OnUserLongClickListener) : PagedListAdapter<User, UserViewHolder>(Companion) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        Log.d("Aled","truc")

        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this, onUserClickListener, onUserLongClickListener) }
    }

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}