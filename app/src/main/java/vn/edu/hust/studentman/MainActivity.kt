package vn.edu.hust.studentman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog.Builder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students, ::showEditStudentDialog, ::showDeleteStudentDialog)

    val studentRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_students)
    val addStudentButton = findViewById<Button>(R.id.btn_add_new)

    studentRecyclerView.run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    addStudentButton.setOnClickListener {
      showAddStudentDialog()
    }
  }

  private fun showEditStudentDialog(currentName: String, currentId: String, position: Int) {
    val builder: AlertDialog.Builder = Builder(this)
    val inflater = layoutInflater
    val dialogView: View = inflater.inflate(R.layout.student_dialog, null)
    builder.setView(dialogView)

    val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)
    val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)
    val saveButton = dialogView.findViewById<Button>(R.id.button_save)

    dialogTitle.text = "Chỉnh sửa sinh viên"

    editName.setText(currentName)
    editId.setText(currentId)

    val dialog: AlertDialog = builder.create()

    cancelButton.setOnClickListener { view: View? -> dialog.dismiss() }

    saveButton.setOnClickListener { view: View? ->
      val newName = editName.text.toString()
      val newId = editId.text.toString()
      students[position] = StudentModel(newName, newId)
      studentAdapter.notifyItemChanged(position)
      dialog.dismiss()
    }

    dialog.show()
  }

  private fun showAddStudentDialog() {
    val builder: AlertDialog.Builder = Builder(this)
    val inflater = layoutInflater
    val dialogView: View = inflater.inflate(R.layout.student_dialog, null)
    builder.setView(dialogView)

    val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)
    val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)
    val saveButton = dialogView.findViewById<Button>(R.id.button_save)

    dialogTitle.text = "Thêm sinh viên"

    val dialog: AlertDialog = builder.create()

    cancelButton.setOnClickListener { view: View? -> dialog.dismiss() }

    saveButton.setOnClickListener { view: View? ->
      val newName = editName.text.toString()
      val newId = editId.text.toString()
      students.add(0, StudentModel(newName, newId))
      studentAdapter.notifyItemInserted(0)
      dialog.dismiss()
    }

    dialog.show()
  }

  private fun showDeleteStudentDialog(position: Int) {
    val builder: AlertDialog.Builder = Builder(this)
    builder.setMessage("Bạn có thực sự muốn xóa sinh viên này không?")
      .setPositiveButton("Yes") { dialog, id ->
        val removedStudent = students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)
        Snackbar.make(findViewById(R.id.main), "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            students.add(position, removedStudent)
            studentAdapter.notifyItemInserted(position)
          }.show()
      }
      .setNegativeButton("No") { dialog, id ->
        dialog.dismiss()
      }
    builder.create().show()
  }
}