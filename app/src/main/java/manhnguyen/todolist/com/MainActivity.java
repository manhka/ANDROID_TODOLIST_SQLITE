package manhnguyen.todolist.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewCV;
    ArrayList<CongViec> congViecArrayList;
    CongViecAdapter adapter;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DataBase(this, "ghichu.splite", null, 1);
        listViewCV = (ListView) findViewById(R.id.listViewCV);
        congViecArrayList = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cv, congViecArrayList);
        listViewCV.setAdapter(adapter);
        //tao database

        // tao bang
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCv VARCHAR(100))");
        // them data
//        dataBase.QueryData("INSERT INTO CongViec VALUES(null,'Bai1')");
//        dataBase.QueryData("INSERT INTO CongViec values(null,'Bai2')");
        // lay data
        GetDataCongViec();

    }

    private void GetDataCongViec() {
        Cursor dataCv = dataBase.GetData("SELECT * FROM CongViec");
        congViecArrayList.clear();
        while (dataCv.moveToNext()) {
            String ten = dataCv.getString(1);
            int id = dataCv.getInt(0);
            congViecArrayList.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    // them nut menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_cv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.iconThem) {
            DialogThemCV();
        }
        return super.onOptionsItemSelected(item);
    }

    //tao dialog
    private void DialogThemCV() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_themcv);
        EditText editTenCV = (EditText) dialog.findViewById(R.id.editTextThemCV);
        Button nutThem = (Button) dialog.findViewById(R.id.buttonThemCV);
        Button nutHuy = (Button) dialog.findViewById(R.id.buttonHuyCV);
        // them cong viec moi
        nutThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CVmoi = editTenCV.getText().toString();
                if (CVmoi.equals("") || CVmoi.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập tên công việc", Toast.LENGTH_SHORT).show();
                } else {
                    dataBase.QueryData("INSERT INTO CongViec VALUES(null,'" + CVmoi + "')");
                    Toast.makeText(MainActivity.this, "Đã Thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
//        // hủy việc thêm công việc mới
        nutHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // dialog xoa cong viec
    public void DialogXoaCV(String tenCV, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa công viêc '" + tenCV + "' không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dataBase.QueryData("DELETE FROM CongViec WHERE Id='" + id + "'");
                Toast.makeText(MainActivity.this, "Đã xóa công việc +'"+tenCV+"'", Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    // dialog sua cong viec
    public void DialogSuaCv(String ten, int id) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suacv);
        EditText tenSua = (EditText) dialog.findViewById(R.id.editTextSua);
        Button huy = (Button) dialog.findViewById(R.id.buttonHuySua);
        Button sua = (Button) dialog.findViewById(R.id.buttonSua);
        // sua cong viec
        tenSua.setText(ten);
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = tenSua.getText().toString().trim();
                if (tenMoi.equals("") || tenMoi.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nhập tên công việc mới để sửa", Toast.LENGTH_SHORT).show();
                } else {
                    dataBase.QueryData("UPDATE  CongViec SET TenCv='" + tenMoi + "' WHERE Id='" + id + "'");
                    Toast.makeText(MainActivity.this, "Đã Sửa", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
        // huy viec sua
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}