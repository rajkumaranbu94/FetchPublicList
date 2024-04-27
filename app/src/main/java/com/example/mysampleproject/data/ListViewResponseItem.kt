import android.os.Parcel
import android.os.Parcelable

data class ListViewResponseItem(
    val body: String?,
    val id: Int,
    val title: String?,
    val userId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(body)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListViewResponseItem> {
        override fun createFromParcel(parcel: Parcel): ListViewResponseItem {
            return ListViewResponseItem(parcel)
        }

        override fun newArray(size: Int): Array<ListViewResponseItem?> {
            return arrayOfNulls(size)
        }
    }
}