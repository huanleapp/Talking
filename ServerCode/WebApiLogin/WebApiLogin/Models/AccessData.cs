using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace WebApiLogin.Models
{
    public class AccessData
    {
        #region "Property"
        private SqlConnection cnn;
        private SqlCommand cmd;
        private DataSet dts;
        private DataTable tbl;
        private SqlDataReader dr;
        private SqlDataAdapter da;
        private SqlTransaction tran;
        private SqlCommandBuilder cb;
        private readonly string connectionString = "";
        #endregion

        #region "contructor"
        public AccessData(string connectionStringPara)
        {

            this.connectionString = connectionStringPara;
        }
        #endregion
        #region "Process SQL server database"

        /// <summary>
        ///     Get Connection String SQL server Database
        /// </summary>
        /// <returns>
        ///     A System.Data.SqlClient.SqlConnection value...
        /// </returns>
        public SqlConnection BolConnect()
        {
            return new SqlConnection(connectionString);

        }

        public bool SaveTableIntoDatabase(string tblName, DataTable dt)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                string strSql = string.Format("SELECT * FROM {0}", tblName);
                da = new SqlDataAdapter(strSql, cnn);
                cb = new SqlCommandBuilder(da);
                da.Update(dt);
                cnn.Close();
                return true;
            }
            catch 
            {
                
            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                da.Dispose();
            }
            return false;
        }

        /// <summary>
        ///      Excute SQL text SQLserver Datatbase 
        /// </summary>
        /// <param name="strSql" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A bool value...
        /// </returns>
        public Boolean BolExcuteSQL(string strSql, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                tran = cnn.BeginTransaction();
                cmd = new SqlCommand() { CommandType = CommandType.Text, CommandText = strSql, Connection = cnn, Transaction = tran, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }

                cmd.ExecuteNonQuery();
                tran.Commit();
                cnn.Close();
                return true;
            }
            catch 
            {tran.Rollback();
            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                tran.Dispose();
            }
            return false;
        }


        /// <summary>
        ///     Excute Store Proceduce SQLserver Datatbase return Boolean 
        /// </summary>
        /// <param name="proceduceName" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A bool value...
        /// </returns>
        public Boolean BolExcuteSP(string proceduceName, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                tran = cnn.BeginTransaction();
                cmd = new SqlCommand() { CommandType = CommandType.StoredProcedure, CommandText = proceduceName, Connection = cnn, Transaction = tran, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }

                cmd.ExecuteNonQuery();
                tran.Commit();
                cnn.Close();
                return true;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                tran.Dispose();
            }
            return false;
        }


        /// <summary>
        ///     Read Data Into Dataset by Store Proceduce
        /// </summary>
        /// <param name="proceduceName" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.DataSet value...
        /// </returns>
        public DataSet DtsReadDataSP(string proceduceName, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.StoredProcedure, CommandText = proceduceName, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                da = new SqlDataAdapter(cmd);
                dts = new DataSet();
                da.Fill(dts);
                cnn.Close();
                return dts;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                da.Dispose();
            }
            return null;
        }

        /// <summary>
        ///     Read Data Into Dataset by SQL text
        /// </summary>
        /// <param name="strSQL" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.DataSet value...
        /// </returns>
        public DataSet DtsReadDataSQL(string strSQL, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.Text, CommandText = strSQL, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                da = new SqlDataAdapter(cmd);
                dts = new DataSet();
                da.Fill(dts);
                cnn.Close();
                return dts;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                da.Dispose();
            }
            return null;
        }

        /// <summary>
        ///     Read Data Into Datatable by Store Proceduce 
        /// </summary>
        /// <param name="proceduceName" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.DataTable value...
        /// </returns>
        public DataTable TblReadDataSP(string proceduceName, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.StoredProcedure, CommandText = proceduceName, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                da = new SqlDataAdapter(cmd);
                tbl = new DataTable();
                da.Fill(tbl);
                cnn.Close();
                return tbl;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                da.Dispose();
            }
            return null;
        }

        /// <summary>
        ///     Read Data Into Datatable by SQL text
        /// </summary>
        /// <param name="strSQL" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.DataTable value...
        /// </returns>
        public DataTable TblReadDataSQL(string strSQL, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.Text, CommandText = strSQL, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                da = new SqlDataAdapter(cmd);
                tbl = new DataTable();
                da.Fill(tbl);
                cnn.Close();
                return tbl;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                da.Dispose();
            }
            return null;
        }

        public DataTable TblReadDataSQL(string strSQL, SqlParameter[] arrParameter, DataTable defaulTable)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.Text, CommandText = strSQL, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                da = new SqlDataAdapter(cmd);
                tbl = new DataTable();
                da.Fill(tbl);
                cnn.Close();
                return tbl;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                da.Dispose();
            }
            return defaulTable;
        }



        /// <summary>
        ///      Read Data Into DataReader by Store Proceduce 
        /// </summary>
        /// <param name="proceduceName" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.SqlClient.SqlDataReader value...
        /// </returns>
        public SqlDataReader DreadReadDataSP(string proceduceName, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.StoredProcedure, CommandText = proceduceName, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                dr = cmd.ExecuteReader();
                cnn.Close();
                return dr;
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                dr.Dispose();
            }
            return null;
        }

        /// <summary>
        ///    Read Data Into DataReader by SQL Text 
        /// </summary>
        /// <param name="strSQL" type="string">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <param name="arrParameter" type="System.Data.SqlClient.SqlParameter[]">
        ///     <para>
        ///         
        ///     </para>
        /// </param>
        /// <returns>
        ///     A System.Data.SqlClient.SqlDataReader value...
        /// </returns>
        public SqlDataReader DreadReadDataSQL(string strSQL, SqlParameter[] arrParameter)
        {
            try
            {
                cnn = BolConnect();
                cnn.Open();
                cmd = new SqlCommand() { CommandType = CommandType.Text, CommandText = strSQL, Connection = cnn, CommandTimeout = 0 };
                if (arrParameter != null) //Trường hợp có tham số
                {
                    cmd.Parameters.Clear();
                    foreach (SqlParameter t in arrParameter)
                        cmd.Parameters.Add(t);
                }
                dr = cmd.ExecuteReader();
                cnn.Close();
                return dr; 
            }
            catch 
            {

            }
            finally
            {
                if (cnn.State != ConnectionState.Closed) { cnn.Close(); }
                cnn.Dispose();
                cmd.Dispose();
                dr.Dispose();
            }
            return null;
        }



        #endregion
    }
}