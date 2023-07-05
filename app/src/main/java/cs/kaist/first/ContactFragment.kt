package cs.kaist.first

import FavoriteAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactFragment : Fragment() {
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private val PERMISSIONS_REQUEST_WRITE_CONTACTS = 100

    var contactItems = ArrayList<ContactModel>()
    var contactAdapter : ContactAdapter ?= null

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val updatedMemo2 = data?.getStringExtra("memo2")
            val position2 = data?.getIntExtra("position2", 0)
            if (updatedMemo2 != null && position2 != -1) {
                contactItems[position2!!].memo = updatedMemo2
                println("updatedMemo2")
                println(updatedMemo2)
                contactAdapter?.notifyItemChanged(position2)
            }
        }
    }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_contact, container, false)

            super.onViewCreated(view, savedInstanceState)
            var id = "0"
            var name = "이황근"
            var number = "010-3909-4581"
            var email = "cowcow24@naver.com"
            var group = "friend"
            var thumnailId: String? = null

            //contactItems.add(ContactModel(name,number,email,group))
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.WRITE_CONTACTS
                ), 99
            )
            val Uri = ContactsContract.Contacts.CONTENT_URI
            val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.CommonDataKinds.Note.DATA1
            )
            val projection2 = arrayOf(
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.CommonDataKinds.Email.DATA
            )


            val cursor =
                requireActivity().contentResolver.query(phoneUri, projection, null, null, null)
            while (cursor?.moveToNext() ?: false) {
                id = cursor?.getString(0).toString()
                name = cursor?.getString(1).toString()
                number = cursor?.getString(2).toString()
                email = cursor?.getString(3).toString()
                group = cursor?.getString(4).toString()
                thumnailId = cursor?.getString(5)
                println(cursor?.getString(6))
                val phone = ContactModel(id, name, number, email, group, thumnailId)
                // 개별 전화번호 데이터 생성

                contactItems.add(phone)
                // 결과목록에 더하기

            }

            val cursor2 = requireActivity().contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                projection2,
                null,
                null,
                null
            )
            while (cursor2?.moveToNext() ?: false) {
                println(cursor2?.getString(0).toString())
                println(cursor2?.getString(1).toString())

            }
            cursor2?.close()


            val noteProjection = arrayOf(
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.CommonDataKinds.Note.NOTE
            )
            var count = 1
            val cursor3 = requireActivity().contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                noteProjection,
                ContactsContract.Data.MIMETYPE + " = ?",
                arrayOf(ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE),
                null
            )

            while (cursor3?.moveToNext() == true) {
                val noteContactId = cursor3.getString(0)
                val noteColumnIndex = cursor3.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE)

                var note: String? = null
                if(noteColumnIndex != -1) {
                    note = cursor3.getString(noteColumnIndex)
                }
                contactItems[count++].memo = note
                println(noteContactId)
                println(note)
            }



            println(contactItems)
            cursor3?.close()

            //println(contactItems)
            contactItems.sortBy { it.name }




            cursor?.close()

            val favoriteContactItems = ArrayList<ContactModel>()


            val new_projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.Contacts.STARRED
            )

            val selection = ContactsContract.Contacts.STARRED + "=?"
            val selectionArgs = arrayOf("1")

            val new_cursor =
                requireActivity().contentResolver.query(phoneUri, new_projection, selection, selectionArgs, null)

            while (new_cursor?.moveToNext() ?: false) {
                val id = new_cursor?.getString(0)
                val name = new_cursor?.getString(1)
                val number = new_cursor?.getString(2)
                val thumnailId = new_cursor?.getString(3)
                val isStarred = new_cursor?.getString(4)?.toInt() == 1

                if (isStarred) {
                    val contact = ContactModel(id, name, number, "", "", thumnailId, "")
                    favoriteContactItems.add(contact)
                }
            }

            new_cursor?.close()

            println(favoriteContactItems)
            contactAdapter = ContactAdapter(contactItems, requireContext())
            val contactRecyclerView = view.findViewById<RecyclerView>(R.id.contactRecyclerView)
            contactRecyclerView.layoutManager = LinearLayoutManager(context)
            contactRecyclerView.adapter = contactAdapter


            val favoriteAdapter = FavoriteAdapter(favoriteContactItems, requireContext())
            val favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteListview)
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            favoriteRecyclerView.layoutManager = layoutManager
            favoriteRecyclerView.adapter = favoriteAdapter


            val plusImageView = view.findViewById<ImageView>(R.id.plusImageView)
            plusImageView.setOnClickListener {
                val plusIntent = Intent(Intent.ACTION_INSERT)
                plusIntent.data = android.net.Uri.parse("content://contacts/people")
                startActivity(plusIntent)
            }
            println(contactItems)


            return view;

        }

        fun refreshAdapter() {
            //contactAdapter?.updateMemo(position)
            contactAdapter?.notifyDataSetChanged()
            println("refresh")
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        }


}