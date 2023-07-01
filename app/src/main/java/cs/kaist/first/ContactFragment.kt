package cs.kaist.first

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactFragment : Fragment() {
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        super.onViewCreated(view, savedInstanceState)


        var name = "이황근"
        var number = "010-3909-4581"
        var email = "cowcow24@naver.com"
        var group = "friend"
        var thumnailId: String? = null
        var contactItems = ArrayList<ContactModel>()
        //contactItems.add(ContactModel(name,number,email,group))
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_CONTACTS), 99)
        val Uri = ContactsContract.Contacts.CONTENT_URI
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            , ContactsContract.CommonDataKinds.Phone.NUMBER
            , ContactsContract.CommonDataKinds.Email.DATA
            , ContactsContract.CommonDataKinds.Phone.TYPE
            , ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
            )

        val cursor = requireActivity().contentResolver.query(phoneUri, projection, null, null, null)
        while(cursor?.moveToNext()?:false) {
            name = cursor?.getString(1).toString()
            number = cursor?.getString(2).toString()
            email = cursor?.getString(3).toString()
            group = cursor?.getString(4).toString()
            thumnailId = cursor?.getString(5)
            println(thumnailId)
            val phone = ContactModel(name, number,email,group,thumnailId)
            // 개별 전화번호 데이터 생성

            contactItems.add(phone)
            // 결과목록에 더하기

        }
        println(contactItems)
        contactItems.sortBy { it.name }
        cursor?.close()
        val contactAdapter = ContactAdapter(contactItems,requireContext())
        val contactRecyclerView = view.findViewById<RecyclerView>(R.id.contactRecyclerView)
        contactRecyclerView.layoutManager = LinearLayoutManager(context)
        contactRecyclerView.adapter = contactAdapter


        println(contactItems)
        return view;

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



    }


}