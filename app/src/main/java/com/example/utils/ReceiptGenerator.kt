package com.example.nazahapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import com.example.nazahapp.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReceiptGenerator {
    suspend fun generateReceipt(
        context: Context,
        product: ProductModel,
        customerName: String,
        customerAddress: String,
        customerPhone: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = document.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint()
            paint.color = Color.BLACK
            
            paint.textSize = 40f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("NAZAH Handmade Jewelry", 50f, 80f, paint)
            
            paint.textSize = 20f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText("Receipt", 50f, 120f, paint)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = dateFormat.format(Date())
            canvas.drawText("Date: $date", 50f, 160f, paint)

            paint.textSize = 18f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("Customer Details:", 50f, 220f, paint)
            
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText("Name: $customerName", 50f, 250f, paint)
            canvas.drawText("Address: $customerAddress", 50f, 280f, paint)
            canvas.drawText("Phone: $customerPhone", 50f, 310f, paint)

            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("Order Details:", 50f, 370f, paint)
            
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText("Product Name: ${product.name}", 50f, 400f, paint)
            canvas.drawText("Product Code: ${product.productCode}", 50f, 430f, paint)
            
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            paint.textSize = 22f
            canvas.drawText("Total Price: ৳${product.price}", 50f, 480f, paint)

            document.finishPage(page)

            // FIXED: Using App-specific external storage to bypass Android 10+ permission restrictions
            val downloadsDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, "NAZAH_Receipt_${System.currentTimeMillis()}.pdf")
            val outputStream = FileOutputStream(file)
            document.writeTo(outputStream)
            document.close()
            outputStream.close()

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Receipt saved to App Downloads", Toast.LENGTH_LONG).show()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
