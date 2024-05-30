package com.example.buyergetter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyergetter.model.Shop
import com.example.buyergetter.viewmodel.ProductAdapter
import com.example.buyergetter.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private lateinit var adapter: ProductAdapter
    private val productViewModel: ProductViewModel by activityViewModels()

    companion object {
        private const val ARG_SHOP = "shop"

        fun newInstance(shop: Shop): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putParcelable(ARG_SHOP, shop)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.product_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter()
        recyclerView.adapter = adapter

        val shop: Shop? = arguments?.getParcelable(ARG_SHOP) as? Shop
        shop?.let {
            val productLiveData = productViewModel.getProductsForShop(it.shopId)
            productLiveData.observe(viewLifecycleOwner) { productList ->
                if (productList != null) {
                    adapter.submitList(productList)
                }
            }
        }

        return view
    }
}
