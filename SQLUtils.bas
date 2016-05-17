Type=StaticCode
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub TableExists(SQL As SQL, targetTbl As String) As Boolean ' Checks for table existing
   Dim resCurs As Cursor
   Dim ans As Boolean
   Dim ExStmnt As String
   
      ExStmnt = "SELECT name FROM sqlite_master WHERE type='table' AND name='" & targetTbl & "'"
      resCurs = SQL.ExecQuery(ExStmnt)
      ans = (resCurs.RowCount = 1)
      resCurs.close
      
      Return ans
End Sub

Sub Table_AddColumns(SQL As SQL, TableName As String, FieldsAndTypes As Map) ' Update an existing table and add columns
	For i = 0 To FieldsAndTypes.Size - 1
		Dim sb, field, ftype As String
		field = FieldsAndTypes.GetKeyAt(i)
		ftype = FieldsAndTypes.GetValueAt(i)
		sb = "ALTER TABLE [" & TableName & "] ADD COLUMN [" & field & "] " & ftype
		 Dim fExist As Boolean = Table_FieldExists(SQL, TableName, field)
		 If fExist = False Then SQL.ExecNonQuery(sb)
	Next
End Sub

Sub Table_CountRows(SQL As SQL, TableName As String) As Int ' Return the number of rows in a database table
	Dim RowTotal As Int
	RowTotal = SQL.ExecQuerySingleResult("SELECT count(*) FROM [" & TableName & "]")
	Return RowTotal
End Sub

Sub Table_FieldExists(SQL As SQL, TableName As String, FieldName As String) As Boolean ' Returns True if the column name already exists in Table
	Dim FieldList As List
	FieldList = Table_FieldNames(SQL, TableName)
	For i = 0 To FieldList.Size - 1
		If FieldName.ToLowerCase = FieldList.Get(i) Then
			Return True
		End If
	Next
	Return False
End Sub

Sub Table_FieldNames(SQL As SQL, TableName As String) As List ' Returns a list of the table column names as a list
	Dim res1 As List
	res1.Initialize
	Dim cur As Cursor
	cur = SQL.ExecQuery("PRAGMA table_info ([" & TableName & "])")
	If cur.RowCount = 0 Then
		Log("No records found.")
		Return Null
	End If
	For i = 0 To cur.RowCount - 1
		cur.Position = i
		res1.Add(cur.GetString("name").ToLowerCase)
	Next
	cur.close
	Return res1
End Sub

Sub Table_Information(SQL As SQL, TableName As String) As Map ' Returns the table information as a map
	Dim res1 As Map
	Dim res As Map
	res1.Initialize
	Dim cur As Cursor
	cur = SQL.ExecQuery("PRAGMA table_info ([" & TableName & "])")
	If cur.RowCount = 0 Then
		Log("No records found.")
		Return Null
	End If
	For i = 0 To cur.RowCount - 1
		cur.Position = i
		res.Initialize
		res.put("ID", cur.GetString("cid"))
		res.put("Name", cur.GetString("name"))
		res.Put("Type", cur.GetString("type"))
		res.Put("Default value", cur.GetString("dflt_value"))	
		res.Put("Not null", cur.GetString("notnull"))
		res.Put("Primary key", cur.GetString("pk"))
		res1.Put(i, res)
	Next
	cur.close
	Return res1
End Sub

Sub Table_InsertMap(SQL As SQL, TableName As String, m As Map) ' Insert a map into the database table
	Dim sb, columns, values As StringBuilder
	SQL.BeginTransaction
	Try
		sb.Initialize
		columns.Initialize
		values.Initialize
		Dim listOfValues As List
		listOfValues.Initialize
		sb.Append("INSERT INTO [" & TableName & "] (")
		For i2 = 0 To m.Size - 1
			Dim col As String
			Dim value As Object
			col = m.GetKeyAt(i2)
			value = m.GetValueAt(i2)
			If i2 > 0 Then
				columns.Append(", ")
				values.Append(", ")
			End If
			columns.Append("[").Append(col).Append("]")
			values.Append("?")
			listOfValues.Add(value)
		Next
		sb.Append(columns.ToString).Append(") VALUES (").Append(values.ToString).Append(")")
		Log("InsertMap : " & sb.ToString)
		SQL.ExecNonQuery2(sb.ToString, listOfValues)
		SQL.TransactionSuccessful
	Catch
		ToastMessageShow(LastException.Message, True)
		Log(LastException)
	End Try
	SQL.EndTransaction
End Sub

Public Sub Table_ListOfField(SQL As SQL, TableName As String, KeyField As String) As List ' Returns the list of values from a table
	Dim c As Cursor
	TableName = TableName.ToLowerCase
	If TableName.StartsWith("select") = True Then
		c = SQL.ExecQuery(TableName)
	Else
		c = SQL.ExecQuery("SELECT [" & KeyField & "] FROM [" & TableName & "]")
	End If
	Dim res As List
	Dim strV As String
	res.Initialize
	For row = 0 To c.RowCount - 1
		c.Position = row
		strV = c.GetString2(0).Trim
		If strV.Length > 0 Then res.Add(strV)
	Next
	Return res
End Sub

Public Sub Table_RecordExists(SQL As SQL, TableName As String, FldName As String, FldValue As String) As Boolean ' Checks for a record existing in a table
	Return SQL.ExecQuerySingleResult2("SELECT count(" & FldName & ") FROM [" & TableName & "] WHERE [" & FldName & "] = ?", Array As String(FldValue)) > 0
End Sub

Sub Table_Rename(SQL As SQL, OldName As String, NewName As String) ' Rename an existing table
	SQL.ExecNonQuery("ALTER TABLE [" & OldName & "] RENAME TO [" & NewName & "]")
End Sub

Sub CreateIndex(SQL As SQL, TableName As String, IndexName As String, Fields As String, Unique As Boolean) ' Create Index
   Dim query, uni As String
   uni = ""
   If Unique=True Then uni = "UNIQUE"      
   query = "CREATE " & uni & " INDEX IF NOT EXISTS " & IndexName & " ON [" & TableName & "] (" & Fields & ")"
   Log("CreateIndex: " & query)
   SQL.ExecNonQuery(query)
End Sub

Sub DropIndex(SQL As SQL, IndexName As String) ' Delete Index
   Dim query As String   
   query = "DROP INDEX IF EXISTS " & IndexName 
   Log("DropIndex: " & query)
   SQL.ExecNonQuery(query)
End Sub

' return all records of the query as a list of maps
' all column names are in lowercase
'Sub Table_ExecuteMaps(Query As String, StringArgs() As String) As List
'Dim cur As Cursor
'Dim colValue As String
'Dim colName As String
'Dim Limit As Int
'If StringArgs <> Null Then
'' cur = SQLite.ExecQuery2(Query, StringArgs)
'Else
'' cur = SQLite.ExecQuery(Query)
'End If
'Log("ExecuteMaps: " & Query)
'Dim xTable As List
'xTable.Initialize
'Limit = cur.RowCount
'For row = 0 To Limit - 1
'cur.Position = row
'Dim m As Map
'm.Initialize
'For col = 0 To cur.ColumnCount - 1
'colName = cur.GetColumnName(col).tolowercase
'colValue = cur.GetString2(col)
'If colValue = Null Then colValue = ""
'm.Put(colName, colValue)
'Next
'xTable.Add(m)
'Next
'cur.Close
'Return xTable
'End Sub