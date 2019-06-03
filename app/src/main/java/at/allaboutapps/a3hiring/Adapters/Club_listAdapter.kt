package at.allaboutapps.a3hiring.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import at.allaboutapps.a3hiring.DataObject.ClubListObject
import at.allaboutapps.a3hiring.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.screen_association_rowitem.view.*
import android.view.LayoutInflater
import at.allaboutapps.a3hiring.Utils.Constants


/**
 * Created by Muthukumar Neelamegam on 4/12/2019.
 * android-hiring
 */
class Club_listAdapter(private val clublist: List<ClubListObject>) : RecyclerView.Adapter<Club_listAdapter.ClubHolder>() {

    override fun getItemCount(): Int {
        return clublist.size
    }

    override fun onBindViewHolder(holder: Club_listAdapter.ClubHolder, position: Int) {
        val itemClubs = clublist[position]
 
        holder.bindClubs(itemClubs)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Club_listAdapter.ClubHolder {

       val  inflatedView = if (Constants.ListviewType)//if true then grid
            LayoutInflater.from(parent.context).inflate(R.layout.screen_association_gridrowitem, parent, false)
        else//if false then list
            LayoutInflater.from(parent.context).inflate(R.layout.screen_association_rowitem, parent, false)


        return ClubHolder(inflatedView)
    }

    class ClubHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var club: ClubListObject? = null

        init {
            v.setOnClickListener { this }
        }

        override fun onClick(v: View?) {
            Log.e("RecyclerView", "CLICK!")
        }

        fun bindClubs(clubs: ClubListObject) {
            this.club = clubs

            Picasso.get()
                    .load(club!!.image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .priority(Picasso.Priority.HIGH)
                    .resize(100, 100)
                    .into(view.imgvw_logo)

            view.txtvw_clubname.text = club!!.name
            view.txtvw_countryassociation.text = club!!.country
            view.txtvw_associationvalue.text = "Points: " + club!!.value
        }

    }


}//End