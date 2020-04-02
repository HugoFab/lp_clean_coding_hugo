package fr.hugo.template.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import fr.hugo.template.R
import fr.hugo.template.data.model.User
import fr.hugo.template.ui.adapter.UserListAdapter
import fr.hugo.template.ui.viewholder.OnUserClickListener
import fr.hugo.template.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.list_user_fragment.*

class ListFragment : Fragment(),OnUserClickListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            userViewModel = ViewModelProvider(this,UserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserListAdapter(this,this)
        recycler_view_user.apply {
            adapter = userAdapter

        }
        userViewModel.userList.observe(this) {
            Log.d("hello",it.toString())
            userAdapter.submitList(it)
        }
    }

    override fun invoke(view: View, character: User) {

    }


}