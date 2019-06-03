package at.allaboutapps.a3hiring.CoreModules


import android.app.AlertDialog
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.*
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import at.allaboutapps.a3hiring.Adapters.Club_listAdapter
import at.allaboutapps.a3hiring.DataObject.ClubListObject
import at.allaboutapps.a3hiring.R
import at.allaboutapps.a3hiring.Utils.Application
import at.allaboutapps.a3hiring.Utils.Constants
import at.allaboutapps.a3hiring.databinding.ClubListBinding
import com.dezlum.codelabs.getjson.GetJson
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import kotlin.collections.ArrayList


/**
 * Created by Muthukumar Neelamegam on 4/12/2019.
 * android-hiring
 */


class ClubList : Application() {
    //***************************************************************************************

    private var clubrecyclerView: RecyclerView? = null
    var mBinding: ClubListBinding? = null
    private val TAG: String = "MainActivity"
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: Club_listAdapter
    val LIST_STATE_KEY = "recycler_list_state"
    var listState: Parcelable? = null

    var VIEWTYPE=1
    var SORTNAME=1
    var SORTVALUE=1
    var SORTTYPE=0//default
    //***************************************************************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getInitialize()

        controllisteners()

        loadDefaultRecyclerView()


    }
    //***************************************************************************************

    fun getInitialize() {


        mBinding = DataBindingUtil.setContentView(this, R.layout.club_list)

        VIEWTYPE=2
        SORTNAME=1
        SORTVALUE=1
        SORTTYPE=0

        ReloadRecycler(VIEWTYPE, SORTNAME, SORTVALUE, SORTTYPE)//default : list and sort a-Z

    }
    //***************************************************************************************

    private fun clubListInit(
        clubsList: ArrayList<ClubListObject>,
        sortName: Int,
        sortValue: Int,
        sortType: Int
    ) {
        clubrecyclerView = findViewById(R.id.recyclerViewClub)



        if (!Constants.ListviewType) {//List
            clubrecyclerView!!.setLayoutManager(LinearLayoutManager(this))

            // adding inbuilt divider line
            clubrecyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        } else {//Grid
            clubrecyclerView!!.setLayoutManager(GridLayoutManager(this, 2))

            // adding inbuilt divider line
            clubrecyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            clubrecyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))

        }


        //provides basic animations on remove, add, and move events that happen to the items in a RecyclerView.
        //RecyclerView uses a DefaultItemAnimator by default.
        clubrecyclerView!!.itemAnimator = DefaultItemAnimator()


        when (sortType) {
            1//Alphabets
            -> {
                //sorting if 1=az else za
                mAdapter = if(sortName==1)//az
                {
                    var sortedList = clubsList.sortedWith(compareBy({ it.name }))
                    Club_listAdapter(sortedList)

                }else//za
                {
                    var sortedList = clubsList.sortedWith(compareBy({ it.name })).reversed()
                    Club_listAdapter(sortedList)
                }
            }
            2//Numbers
            -> {

                mAdapter = if(sortValue==1) {
                    var sortedList = clubsList.sortedWith(compareBy({ it.value }))
                    Club_listAdapter(sortedList)

                }else{
                    var sortedList = clubsList.sortedWith(compareBy({ it.value })).reversed()
                    Club_listAdapter(sortedList)

                }

            }
            else -> {//default
                var sortedList = clubsList.sortedWith(compareBy({ it.name }))
                mAdapter=  Club_listAdapter(sortedList)
            }
        }



        clubrecyclerView!!.adapter = mAdapter


    }
    //***************************************************************************************

    private fun loadData(): ArrayList<ClubListObject> {
       // val jsonString: String = readJsonFromKotlinFile()
        //val jsonString: String = APIRest.getClubDetails()
        val jsonObject = GetJson().AsString(Constants.URL)
        Log.e("response from server: ", jsonObject)
        val clubsList: ArrayList<ClubListObject> = parseJsonStringToNewsList(jsonObject)
        return clubsList
    }




    //***************************************************************************************

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        // Save list state
        listState = clubrecyclerView!!.layoutManager!!.onSaveInstanceState()
        state.putParcelable(LIST_STATE_KEY, listState)
    }
    //***************************************************************************************

    override fun onRestoreInstanceState(state: Bundle?) {
        super.onRestoreInstanceState(state)
        // Retrieve list state and list/item positions
        if (state != null)
            listState = state.getParcelable(LIST_STATE_KEY)
    }

    override fun onResume() {
        super.onResume()
        if (listState != null) {
            clubrecyclerView!!.layoutManager!!.onRestoreInstanceState(listState)
        }
    }
    //***************************************************************************************

    fun controllisteners() {

    }
    //***************************************************************************************

    fun loadDefaultRecyclerView() {

    }
    //***************************************************************************************


    private fun parseJsonStringToNewsList(jsonString: String): ArrayList<ClubListObject> {
        val clubsList: ArrayList<ClubListObject> = ArrayList<ClubListObject>(0)
        val clubsArray = JSONArray(jsonString)
        var i = 0
        var numIterations = clubsArray.length()
        while (i < numIterations) {
            val newsObject: JSONObject = clubsArray.getJSONObject(i)
            val clubs = ClubListObject()
            clubs.name = newsObject.getString("name")
            clubs.country = newsObject.getString("country")
            clubs.value = newsObject.getInt("value")

            try {
                clubs.image = newsObject.getString("image")
            } catch (e: Exception) {

                clubs.image = newsObject.getString("images")
                clubsList.add(clubs)
                i++
            }


            clubsList.add(clubs)
            i++
        }
        return clubsList
    }
    //***************************************************************************************


    private fun readJsonFromKotlinFile(): String {
        var inputString = ""
        try {
            val inputStream: InputStream = assets.open("clubs.json")
            inputString = inputStream.bufferedReader().use { it.readText() }
            Log.d(TAG, inputString)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
        return inputString
    }

    //***************************************************************************************

    // Within the activity which receives these changes
    // Checks the current device orientation, and toasts accordingly
    override
    fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
    //***************************************************************************************

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //***************************************************************************************

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //TODO: handle sort item click
        //Toast.makeText(this,"menu", Toast.LENGTH_LONG).show()

        ShowDialog()

        return super.onOptionsItemSelected(item)
    }

    private fun ShowDialog() {


        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.screen_filter, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Filters")
        //show dialog
        val mAlertDialog = mBuilder.show()

        val option_list = mAlertDialog.findViewById<View>(R.id.view_list) as LinearLayout
        val option_grid = mAlertDialog.findViewById<View>(R.id.view_grid) as LinearLayout
        val option_name_az = mAlertDialog.findViewById<View>(R.id.sort_name1) as LinearLayout
        val option_name_za = mAlertDialog.findViewById<View>(R.id.sort_name2) as LinearLayout
        val option_points_19 = mAlertDialog.findViewById<View>(R.id.sort_value) as LinearLayout
        val option_points_91 = mAlertDialog.findViewById<View>(R.id.sort_value2) as LinearLayout

        option_list.setOnClickListener {
            VIEWTYPE=1
            SORTNAME=1
            SORTTYPE=0
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME,SORTVALUE, SORTTYPE) }//grid


        option_grid.setOnClickListener {
            VIEWTYPE=2
            SORTNAME=1
            SORTTYPE=0
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME,SORTVALUE,SORTTYPE) }//list


        option_name_az.setOnClickListener {
            SORTNAME=1
            SORTTYPE=1
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME,SORTVALUE,SORTTYPE) }//az


        option_name_za.setOnClickListener {
            SORTNAME=2
            SORTTYPE=1
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME,SORTVALUE,SORTTYPE) }//za


        option_points_19.setOnClickListener {
            SORTVALUE=1
            SORTTYPE=2
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME ,SORTVALUE,SORTTYPE) }//19



        option_points_91.setOnClickListener {
            SORTVALUE=2
            SORTTYPE=2
            mAlertDialog.dismiss()
            ReloadRecycler(VIEWTYPE,SORTNAME ,SORTVALUE,SORTTYPE) }//91




    }


    fun ReloadRecycler(id: Int, sortName:Int, sortValue:Int, sortType:Int) {
        if (id == 2) {//List

            Constants.ListviewType = true
            val clubsList: ArrayList<ClubListObject> = loadData()//Load data

            clubListInit(clubsList, sortName, sortValue, sortType)//Load recycler view

        } else {//Grid
            Constants.ListviewType = false
            val clubsList: ArrayList<ClubListObject> = loadData()//Load data

            clubListInit(clubsList, sortName, sortValue, sortType)//Load recycler view

        }
    }
    //***************************************************************************************

    override fun onBackPressed() {
        Constants.ExitDialog(this)
    }
    //***************************************************************************************


}//End