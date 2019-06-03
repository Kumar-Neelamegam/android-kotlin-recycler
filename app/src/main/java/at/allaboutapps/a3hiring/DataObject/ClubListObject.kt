package at.allaboutapps.a3hiring.DataObject



/**
 * Created by Muthukumar Neelamegam on 4/12/2019.
 * android-hiring
 */

data class ClubListObject(
        val id:Int=0,
        var name:String?="N/A",
        var country: String? = "N/A",
        var value: Int? = 0,
        var image: String? = ""
)