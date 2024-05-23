import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.buyergetter.model.CartItem

@Dao
interface CartDao {
    @Insert
    suspend fun insert(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>> // Changed return type to LiveData

    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
