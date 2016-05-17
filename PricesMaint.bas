Type=Activity
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnExit As Button
	Private btnNext As Button
	Private btnPrev As Button
	Private btnFirst As Button
	Private btnPrevSect As Button
	Private btnNextSect As Button
	Private btnLast As Button
	
	Private lblCommod As Label
	Private lblCommodGroup As Label
	Private lblComGrp As Label
	Private lblGalactic As Label
	Private lblHigh As Label
	Private lblMedium As Label
	Private lblLow As Label
	
	Private edtGalactic As EditText
	Private edtHigh As EditText
	Private edtLow As EditText
	Private edtMedium As EditText

	Private wbvStockPriceList As WebView
	
	Dim SystemName As String
	Dim StationName As String
	Dim SMLMax As Int
	Dim SMLPosition As Int
	Dim SMItem As Int
	
	Dim LoadedType As String
	Dim LoadedLvl As Int
	Dim ItemChanged As Boolean
	
	Dim SystemLocation As List
	Dim GalPriceList As List
	

	Dim Q As String

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("PricesMaint")
		
	SMLPosition = 0
	lblCommodGroup.Text = ""
	lblComGrp.Text = ""
	
	Functions.SetColours(Activity)
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region Setup

Sub FillPriceList
	EditPriceList ' Populates the list of Commodity Prices
	Q = "SELECT G.CommodDesc AS [Commodity], G.GP AS [Galactic Avg Price], G.AvgHP AS [Avg High Price], G.AvgMP AS [Avg Medium Price], G.AvgLP AS [Avg Low Price] FROM GalacticPrices G LEFT JOIN CommodityGroups CG ON G.CommodDesc = CG.CommodDesc ORDER BY CG.ComGrpDesc ASC, G.CommodDesc ASC"
	wbvStockPriceList.LoadHtml(DBUtils.ExecuteHtml(Starter.SQLExec, Q, Null, 0, False))

End Sub

Sub EditPriceList
	Q = "SELECT * FROM GalacticPrices G LEFT JOIN CommodityGroups CG ON G.CommodDesc = CG.CommodDesc ORDER BY CG.ComGrpDesc ASC, G.CommodDesc ASC"
	GalPriceList = DBUtils.ExecuteMemoryTable(Starter.SQLExec, Q, Array As String(StationName), 0)
	SMLMax = GalPriceList.Size - 1
	If SMLMax <= 0 Then
		EditVisible(False)	
	Else
		EditVisible(True)
		DisplayEditItem(SMLPosition)
	End If
	
End Sub

#End Region

#Region Buttons

Sub btnPrev_Click
	If SMLPosition > 0 Then
		SMLPosition = SMLPosition - 1
		FillPriceList
	End If
End Sub

Sub btnNext_Click
	If SMLPosition < SMLMax Then
		SMLPosition = SMLPosition + 1
		FillPriceList
	End If
End Sub

Sub btnFirst_Click
	SMLPosition = 0
	FillPriceList
End Sub

Sub btnLast_Click
	SMLPosition = SMLMax
	FillPriceList
End Sub

Sub btnPrevSect_Click
	Dim val(5) As String
	Dim currCommodGroup As String
	Dim newCommodGroup As String
	Dim changed = False As Boolean
	If SMLPosition > 0 Then
		Do While changed = False
			val = GalPriceList.Get(SMLPosition)
			currCommodGroup = val(3).ToUpperCase
			SMLPosition = SMLPosition - 1
			If SMLPosition = 0 Then
				changed = True
			End If
			val = GalPriceList.Get(SMLPosition)
			newCommodGroup = val(3).ToUpperCase
			If newCommodGroup <> currCommodGroup Then
				changed = True
			End If
		Loop
	End If
	FillPriceList
End Sub

Sub btnNextSect_Click
	Dim val(5) As String
	Dim currCommodGroup As String
	Dim newCommodGroup As String
	Dim changed = False As Boolean
	If SMLPosition < SMLMax Then
		Do While changed = False
			val = GalPriceList.Get(SMLPosition)
			currCommodGroup = val(3).ToUpperCase
			SMLPosition = SMLPosition + 1
			If SMLPosition = SMLMax Then
				changed = True
			End If
			val = GalPriceList.Get(SMLPosition)
			newCommodGroup = val(3).ToUpperCase
			If newCommodGroup <> currCommodGroup Then
				changed = True
			End If
		Loop
	End If
	FillPriceList
End Sub

Sub btnExit_Click
	Activity.Finish
	
End Sub

#End Region

Sub DisplayEditItem(SMLNum As Int)
	Dim Val(5) As String
	
	Val = StationMarketList.Get(SMLNum)
	
	SMItem = Val(0)
	lblComGrp.Text = Val(3).ToUpperCase
	lblCommodGroup.Text = Val(3)
	lblCommod.Text = Val(4).ToUpperCase
	LoadedType = Val(1)
	LoadedLvl = Val(2)

	' SetToggleButtonColour
	
	EditNav

End Sub

Sub EditVisible(Status As Boolean)
	If Status = True Then
		lblCommodGroup.Visible = True
		lblCommod.Visible = True
		lblGalactic.Visible = True
		lblHigh.Visible = True
		lblMedium.Visible = True
		lblLow.Visible = True
		edtGalactic.Visible = True
		edtHigh.Visible = True
		edtMedium.Visible = True
		edtLow.Visible = True
		btnPrev.Visible = True
		btnNext.Visible = True
		btnFirst.Visible = True
		btnLast.Visible = True
		btnPrevSect.Visible = True
		btnNextSect.Visible = True
	Else
		lblCommodGroup.Visible = False
		lblCommod.Visible = False
		lblGalactic.Visible = False
		lblHigh.Visible = False
		lblMedium.Visible = False
		lblLow.Visible = False
		edtGalactic.Visible = False
		edtHigh.Visible = False
		edtMedium.Visible = False
		edtLow.Visible = False
		btnPrev.Visible = False
		btnNext.Visible = False
		btnFirst.Visible = False
		btnLast.Visible = False
		btnPrevSect.Visible = False
		btnNextSect.Visible = False
	End If
	
End Sub

Sub EditNav

	If SMLPosition = 0 Then
		btnPrev.Enabled = False
		btnPrevSect.Enabled = False
		btnFirst.Enabled = False
	Else
		btnPrev.Enabled = True
		btnPrevSect.Enabled = True
		btnFirst.Enabled = True
	End If
	If SMLPosition = SMLMax Then
		btnNext.Enabled = False
		btnNextSect.Enabled = False
		btnLast.Enabled = False
	Else
		btnNext.Enabled = True
		btnNextSect.Enabled = True
		btnLast.Enabled = True
	End If	
	
End Sub

Sub SaveItem
		Dim StockTypeValue As String
		Dim StockLevelValue As Int
		Dim whereclause As Map

		whereclause.Initialize
		whereclause.Put("RecordID", SMItem)

		If StockTypeValue <> LoadedType Then
			ItemChanged = True
		End If
		If StockLevelValue <> LoadedLvl Then
			ItemChanged = True
		End If

	If ItemChanged = True Then
		DBUtils.UpdateRecord(Starter.SQLExec, "StockMarket", "StockTypeDesc", StockTypeValue, whereclause)
		DBUtils.UpdateRecord(Starter.SQLExec, "StockMarket", "StockLevelRank", StockLevelValue, whereclause)
		ItemChanged = False
		btnNext_Click
	End If
	
End Sub

Sub spnStation_ItemClick (Position As Int, Value As Object)
	Dim i As Int
	Dim CursStations As Cursor
	Dim CursStock As Cursor
	Dim CursCommod As Cursor
	Dim record As Map
	Dim econnum As Int
	
	Q = "SELECT * FROM Stations WHERE StationName = ?"
	CursStations = Starter.SQLExec.ExecQuery2(Q, Array As String(Value))
	If CursStations.RowCount > 0 Then
		CursStations.Position = 0 'Sets the row pointer to the first record
		StationName = CursStations.GetString("StationName")
		SystemName = CursStations.GetString("SystemName")
		econnum = CursStations.GetInt("EconomyNum")
	End If
	CursStations.Close
	Q = "SELECT * FROM StockMarket WHERE StationName = ?"
	CursStock = Starter.SQLExec.ExecQuery2(Q, Array As String(StationName))
	If econnum <> 0 Then
		If CursStock.RowCount = 0 Then
			Q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC"
			CursCommod = Starter.SQLExec.ExecQuery(Q)
			If CursCommod.RowCount > 0 Then
				record.Initialize
				record.Put("RecordID", Null)
				record.Put("SystemName", SystemName)
				record.Put("StationName", StationName)
				For i = 0 To CursCommod.RowCount - 1
					CursCommod.Position = i
					record.Put("ComGrpDesc", CursCommod.GetString("ComGrpDesc"))
					record.Put("CommodDesc", CursCommod.GetString("CommodDesc"))
					record.Put("StockTypeDesc", "")
					record.Put("StockLevelRank", 0)
					SQLUtils.Table_InsertMap(Starter.SQLExec,"StockMarket",record)
	
				Next
			End If
			CursCommod.Close
		Else
			Dim R As String
			Dim totalcount As Int
			Q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC"
			CursCommod = Starter.SQLExec.ExecQuery(Q)
			If CursCommod.RowCount > 0 Then
				record.Initialize
				For i = 0 To CursCommod.RowCount - 1
					CursCommod.Position = i
					R = "SELECT COUNT(*) FROM StockMarket WHERE StationName = ? AND CommodDesc = ?"
					totalcount = Starter.SQLExec.ExecQuerySingleResult2(R, Array As String(StationName,CursCommod.GetString("CommodDesc")))
					If totalcount = 0 Then
						record.Put("RecordID", Null)
						record.Put("SystemName", SystemName)
						record.Put("StationName", StationName)
						record.Put("ComGrpDesc", CursCommod.GetString("ComGrpDesc"))
						record.Put("CommodDesc", CursCommod.GetString("CommodDesc"))
						record.Put("StockTypeDesc", "")
						record.Put("StockLevelRank", 0)
						SQLUtils.Table_InsertMap(Starter.SQLExec,"StockMarket",record)
					End If
				Next
			End If
			CursCommod.Close
		End If
		CursStock.Close
	End If

	SMLPosition = 0

	FillMarketList
	
End Sub

Sub acetSystemName_ItemClick (Value As String)
	Dim i As Int
	Dim CursStation As Cursor
	
	' Station spinner
	Q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC"
	CursStation = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursStation.RowCount > 0 Then
		spnStation.Clear
		spnStation.Add("Not Set")
		For i = 0 To CursStation.RowCount - 1
			CursStation.Position = i
			spnStation.Add(CursStation.GetString("StationName"))
		Next
	End If
	spnStation.SelectedIndex = 0
	CursStation.Close

End Sub

Sub acetSystemName_EnterPressed
	Dim i As Int
	Dim CursStation As Cursor
	
	' Station spinner
	Q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC"
	CursStation = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursStation.RowCount > 0 Then
		spnStation.Clear
		spnStation.Add("Not Set")
		For i = 0 To CursStation.RowCount - 1
			CursStation.Position = i
			spnStation.Add(CursStation.GetString("StationName"))
		Next
	End If
	spnStation.SelectedIndex = 0
	CursStation.Close
	
End Sub


Sub edtMedium_EnterPressed
	
End Sub

Sub edtLow_EnterPressed
	
End Sub

Sub edtHigh_EnterPressed
	
End Sub

Sub edtGalactic_EnterPressed
	
End Sub