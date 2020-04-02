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
import kotlinx.android.synthetic.main.details_user_fragment.*
import kotlinx.android.synthetic.main.list_user_fragment.*


class DetailsUserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel


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


        val currentUser = userViewModel.currentUser

        name_user.text = currentUser?.name
        email_user.text = currentUser?.email
        date_creation_field.text = currentUser?.email
        followers_field.text = currentUser?.followers.toString()
        location_field.text = currentUser?.location
        public_repos_field.text = currentUser?.public_repos.toString()
        organisation_url_field.text = currentUser?.organizations_url




    }


}