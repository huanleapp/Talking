using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WebApiLogin.Models;

namespace WebApiLogin.Controllers
{
    public class AccountController : ApiController
    {
        // GET: api/Account
        public IEnumerable<Account_bll> Get()
        {
            Account_dll acc = new Account_dll();
            List<Account_bll> listacc = new List<Account_bll>();
            DataSet ds = acc.Select();
            foreach (DataRow dr in ds.Tables[0].Rows)
            {
                Account_bll acc2 = new Account_bll();
                acc2.TenKhachHang = dr["TA02003"].ToString().Trim();
                acc2.TaiKhoan = dr["TA02001"].ToString().Trim();
                listacc.Add(acc2);
            }
            return listacc;
        }

        // GET: api/Account/5
        public IEnumerable<Account_bll> Get(int Pk)
        {
            Account_dll acc = new Account_dll();
            List<Account_bll> listacc = new List<Account_bll>();
            DataSet ds = acc.SelectByPk(Pk);
            foreach (DataRow dr in ds.Tables[0].Rows)
            {
                Account_bll acc2 = new Account_bll();
                acc2.TenKhachHang = dr["TA02003"].ToString().Trim();
                acc2.TaiKhoan = dr["TA02001"].ToString().Trim();
                listacc.Add(acc2);
            }
            return listacc;
        }

        public bool Get(string User,string Pass)
        {
            Account_dll acc = new Account_dll();
            return acc.CheckLogin(User, Pass);
        }

        // POST: api/Account
        public void Post([FromBody]string value)
        {
            //string a= Request.Form
        }

        // PUT: api/Account/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Account/5
        public void Delete(int id)
        {
        }
    }
}
