using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;

namespace WebApiLogin.Models
{
    public class Account_bll
    {
        public Int32 Pk { get; set; }
        public String TaiKhoan { get; set; }
        public Byte[] MatKhau { get; set; }
        public String TenKhachHang { get; set; }
        public DateTime NgaySinh { get; set; }
        public String SoDienThoai { get; set; }
        public Byte[] Email { get; set; }
        public String GhiChu { get; set; }
        public Int32 LoaiTaiKhoan { get; set; }
        public String Barcode { get; set; }
        public Int32 NhanVienGioiThieu { get; set; }
        public DateTime NgayTao { get; set; }
        public Int32 TrangThai { get; set; }


        public DataSet Select() // lấy các phần tử ra hết
        {
            Account_dll accdll = new Account_dll();
            return accdll.Select();
        }

        public DataSet SelectByID(int Pk) // lấy phần tử theo id
        {
            Account_dll accdll = new Account_dll();
            return accdll.SelectByPk(Pk);
        }
        //public void insert(gia_bll ndtbll)
        //{
        //    gia_dll ndtdll = new gia_dll();
        //    ndtdll.insert(ndtbll);
        //}
        //public void update(gia_bll ndtbll)
        //{
        //    gia_dll ndtdll = new gia_dll();
        //    ndtdll.update(ndtbll);
        //}
        //public void delete(int id1, int id2)
        //{
        //    gia_dll ndtdll = new gia_dll();
        //    ndtdll.delete(id1, id2);
        //}
    }
}