package com.aditya.a_kart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.aditya.a_kart.Model.AddressModel
import com.aditya.a_kart.Model.AllProductModel
import com.aditya.a_kart.Model.OrderModel
import com.aditya.a_kart.databinding.ActivityProductPreviewBinding
import com.aditya.a_kart.databinding.BottomSheetDialogBinding
import com.aditya.a_kart.views.AddressActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ncorti.slidetoact.SlideToActView
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import es.dmoral.toasty.Toasty
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import org.json.JSONObject


class ProductPreviewActivity : AppCompatActivity(), PaymentResultWithDataListener {
    companion object {
        var dataFromProduct = AllProductModel()
    }

    var quantity: Double = 1.0
    var conPrise: Double = 0.0
    lateinit var bSheet: BottomSheetDialog
    lateinit var viewModel: MyViewModel
    var name = ""
    var phoneNo = ""
    var distric = ""
    var pincode = ""
    lateinit var addressModel: AddressModel

    lateinit var binding: ActivityProductPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.aditya.a_kart.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_21e8R4jfMHXPz0")

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getAddress()
        viewModel._getAddress.observe(this@ProductPreviewActivity) {

            for (i in it) {
                addressModel = AddressModel(
                    i.name,
                    i.phoneNo,
                    i.distric,
                    i.houseNo,
                    i.pincode,
                    i.roadName,
                    i.docId,
                )
                name = i.name
                distric = i.distric
                pincode = i.pincode
                phoneNo = i.phoneNo
            }
        }


        binding.cardView.setOnClickListener {
            onBackPressed()
        }

        binding.numberPicker.doOnProgressChanged { numberpicker, progress, vsd ->
            quantity = progress.toDouble()
        }
        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 100



        binding.productImageLoad.load(dataFromProduct.url)
        binding.price.text = "${dataFromProduct.prise} Rs"
        binding.textDesc.text = dataFromProduct.desc
        binding.textView2.text = dataFromProduct.name

        binding.buyNow.setOnClickListener {
            FinalPreview(it)

        }


    }

    private fun FinalPreview(view: View) {
        var isClicked = true
        val mView = BottomSheetDialogBinding.inflate(layoutInflater)
        val bSheet = BottomSheetDialog(this)
        mView.apply {
            cancelBnt.setOnClickListener {
                bSheet.dismiss()
            }
            conPrise = (quantity) * (dataFromProduct.prise).toDouble()
            confirmPrise.text = conPrise.toString()


            myName.text = name
            myDistric.text = distric
            myPincode.text = pincode
            myPhoneNumber.text = phoneNo

            gotoAddress.setOnClickListener {
                startActivity(Intent(this@ProductPreviewActivity, AddressActivity::class.java))
            }

            example.isLocked = true
            addressCard.setOnClickListener {
                if (isClicked) {
                    isClicked = false
                    ll.setBackgroundColor(android.graphics.Color.parseColor("#A8FABF"))
                    example.isLocked = false

                } else {
                    ll.setBackgroundColor(android.graphics.Color.WHITE)
                    isClicked = true
                    example.isLocked = true
                }

            }

            example.onSlideToActAnimationEventListener =
                object : SlideToActView.OnSlideToActAnimationEventListener {
                    override fun onSlideCompleteAnimationEnded(view: SlideToActView) {
                        view.completeIcon = R.drawable.baseline_360_24
                        completePayment()
                        makePayment()
                        view.animate().rotation(3600F * 100).duration = 10000
                    }

                    override fun onSlideCompleteAnimationStarted(
                        view: SlideToActView,
                        threshold: Float
                    ) {
                        view.isRotateIcon = true
                    }

                    override fun onSlideResetAnimationEnded(view: SlideToActView) {

                    }

                    override fun onSlideResetAnimationStarted(view: SlideToActView) {
                        view.isRotateIcon = true
                    }

                }


            example.bumpVibration = 100
            confirmProductImg.load(dataFromProduct.url)

        }
        bSheet.setContentView(mView.root)
        bSheet.show()
    }

    fun completePayment() {

        val db = Firebase.firestore
        val docId = db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document("Cart")
            .collection("Items").document().id


//        db.collection(FirebaseAuth.getInstance().currentUser!!.uid).document("Cart").collection("Items").document(docId).set()

    }

    fun makePayment() {
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
//            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount", conPrise)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "adityayadav@example.com")
            prefill.put("contact", "6392050139")

            options.put("prefill", prefill)
            co.open(activity, options)

        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            Log.d("PAYMENT", e.message!!)
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toasty.success(this@ProductPreviewActivity, "Order Placed", Toasty.LENGTH_LONG).show()
        addToCardData()
        bSheet.dismiss()
    }

    private fun addToCardData() {


        val db = Firebase.firestore
        val docId = db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Orders").document().id
        val uid=FirebaseAuth.getInstance().currentUser?.uid
        val data = OrderModel(
            addressModel,
            dataFromProduct.name,
            dataFromProduct.url,
           conPrise.toString(),
            quantity.toString(),
            docId,
            "Order Processing",
            uid.toString()
        )
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Orders").document(docId).set(data).addOnSuccessListener {
                db.collection("Orders").document(docId).set(data).addOnSuccessListener {
                    Toasty.success(this, "Order Placed to Admin", Toasty.LENGTH_SHORT).show()
                }
            }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.d("KRISHNA", p1!!)
        Toasty.error(this@ProductPreviewActivity, "Order Failed", Toasty.LENGTH_LONG).show()
        bSheet.dismiss()
    }
}