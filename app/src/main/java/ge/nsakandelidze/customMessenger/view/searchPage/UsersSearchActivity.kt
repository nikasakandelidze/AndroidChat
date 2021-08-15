package ge.nsakandelidze.customMessenger.view.searchPage

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.presenter.profile.UsersSearchPresenter
import ge.nsakandelidze.customMessenger.view.homepage.UsersSearchListAdapter
import ge.nsakandelidze.customMessenger.view.profile.IUsersSearch

class UsersSearchActivity : AppCompatActivity(), IUsersSearch {

    private lateinit var usersSearchListRecyclerView: RecyclerView
    private lateinit var presenter: UsersSearchPresenter
    private lateinit var progressBar: ProgressBar
    private var usersList : MutableList<User?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initState()
        initViewComponents()
    }

    private fun initViewComponents(){
        usersSearchListRecyclerView = findViewById<RecyclerView>(R.id.users_list)
        progressBar = findViewById(R.id.loader_progress_bar)
        usersSearchListRecyclerView.adapter = UsersSearchListAdapter(usersList, presenter, progressBar)
        usersSearchListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initState() {
        this.presenter = UsersSearchPresenter(this)
        this.presenter.getAllUsers()
    }

    override fun updateUsersList(users: MutableList<User?>) {
        usersList.clear()
        usersList.addAll(users)
        usersSearchListRecyclerView.adapter?.notifyDataSetChanged()
    }
}