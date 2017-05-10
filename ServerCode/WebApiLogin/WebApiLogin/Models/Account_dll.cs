using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;
using System.Configuration;

namespace WebApiLogin.Models
{
    public class Account_dll
    {
        SqlConnection cn;
        SqlCommand cmd;
        SqlDataAdapter da;
        DataSet ds;
        private readonly AccessData ac;

        public Account_dll()
	{
        cn = new SqlConnection(ConfigurationManager.ConnectionStrings["ctyConnectionString"].ConnectionString);
        ac = new AccessData(ConfigurationManager.ConnectionStrings["ctyConnectionString"].ConnectionString);
        }
        public DataSet Select()
    {
        da = new SqlDataAdapter("select * from TA02", cn);
        ds = new DataSet();
        da.Fill(ds);
        return ds;
    }
        public DataSet SelectByPk(int Pk)
        {
            da = new SqlDataAdapter("select * from TA02 where TA02PK ='" + Pk + "'", cn);
            ds = new DataSet();
            da.Fill(ds);
            return ds;
        }
        public Boolean CheckLogin(string User, string Pass)
        {
            var arrPara = new SqlParameter[2];
            arrPara[0] = new SqlParameter("@TA02001", SqlDbType.NVarChar) { Value = User };
            arrPara[1] = new SqlParameter("@TA02002", SqlDbType.NVarChar) { Value = Pass };
            return ac.BolExcuteSP("sp_TA00003", arrPara);
        }
        //public void insert(gia_bll ndt)
        //{
        //    cmd = new SqlCommand("insert into gia values (@ngay,@macp,@giathamchieu,@giatran,@giasan)",cn);
        //    cmd.Parameters.AddWithValue("@ngay",ndt.ngay);
        //    cmd.Parameters.AddWithValue("@macp",ndt.macp);
        //    cmd.Parameters.AddWithValue("@giathamchieu",ndt.giathamchieu);
        //    cmd.Parameters.AddWithValue("@giatran",ndt.giatran);
        //    cmd.Parameters.AddWithValue("@giasan", ndt.giasan);
        //    cn.Open();
        //    cmd.ExecuteNonQuery();
        //    cn.Close();
        //}
        //public void update(gia_bll ndt)
        //{
        //    cmd = new SqlCommand("update gia set giakhop ='" + ndt.giakhop + "' where ngay = curdate() and macp ='" + ndt.macp + "'", cn);
        //    //cmd.Parameters.AddWithValue("@ngay", ndt.ngay);
        //    //cmd.Parameters.AddWithValue("@macp", ndt.macp);
        //    //cmd.Parameters.AddWithValue("@giathamchieu", ndt.giathamchieu);
        //    //cmd.Parameters.AddWithValue("@giatran", ndt.giatran);
        //    //cmd.Parameters.AddWithValue("@giasan", ndt.giasan);
        //    cn.Open();
        //    cmd.ExecuteNonQuery();
        //    cn.Close();
        //}
        //public void delete(int id1,int id2)
        //{
        //    cmd = new SqlCommand("delete from gia where ngay = @ngay and macp = @macp",cn);
        //    cmd.Parameters.AddWithValue("@ngay", id1);
        //    cmd.Parameters.AddWithValue("@macp", id2);
        //    cn.Open();
        //    cmd.ExecuteNonQuery();
        //    cn.Close();
        //}
    }
}