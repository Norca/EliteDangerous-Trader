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
	Dim m As Math
	
End Sub

#Region Light Year Distances

Sub UpdateSysLY
	Dim I As Int
	Dim Query As String
	Dim result As Double
	Dim CursSystems As Cursor
	Dim whereclause As Map
	
	ProgressDialogShow("Please wait your position in the universe is being calculated...")
	
	
	Query = "SELECT * FROM Systems ORDER BY SystemName ASC"
	CursSystems = Starter.SQLExec.ExecQuery(Query)
	If CursSystems.RowCount > 0 Then
		whereclause.Initialize
		For I = 0 To CursSystems.RowCount - 1
			DoEvents
			CursSystems.Position = I
			whereclause.Put("SystemName",CursSystems.GetString("SystemName"))
			result = DistanceBetween(CursSystems.GetString("SystemName"),Starter.CurrLocation)
			DBUtils.UpdateRecord(Starter.SQLExec,"Systems","LYfromCurrent",result,whereclause)
		Next
	End If
	CursSystems.Close
	
	ProgressDialogHide
	
End Sub

Sub DistanceBetween(Destination As String, Current As String) As Double
	Dim Query As String
	Dim CDS As Cursor, CCS As Cursor
	Dim result As Double

	Query = "SELECT * FROM Systems WHERE SystemName = ?"
	CDS = Starter.SQLExec.ExecQuery2(Query,Array As String(Destination))
	If CDS.RowCount = 0 Then
		Return 0
	End If

	Query = "SELECT * FROM Systems WHERE SystemName = ?"
	CCS = Starter.SQLExec.ExecQuery2(Query,Array As String(Current))
	If CCS.RowCount = 0 Then
		Return 0
	End If

	CDS.Position = 0
	CCS.Position = 0
	
	result = LYFrom(CDS.GetDouble("SpaceX"),CDS.GetDouble("SpaceY"),CDS.GetDouble("SpaceZ"),CCS.GetDouble("SpaceX"),CCS.GetDouble("SpaceY"),CCS.GetDouble("SpaceZ"))
	
	CDS.Close
	CCS.Close
	
	Return result
	
End Sub

Sub LYFrom(DestinationX As Double, DestinationY As Double, DestinationZ As Double, CurrentX As Double, CurrentY As Double, CurrentZ As Double) As Double
	Dim CalcX As Double, CalcY As Double, CalcZ As Double
	Dim LYResult As Double
	
	CalcX = DestinationX - CurrentX
	CalcY = DestinationY - CurrentY
	CalcZ = DestinationZ - CurrentZ
	
	LYResult = Sqrt((CalcX * CalcX) + (CalcY * CalcY) + (CalcZ * CalcZ))

	Return Round2(LYResult,2) 'Returns value as 2 decimal places

End Sub


Sub PlotNewSystem As Coordinate
	Dim i As Int
	Dim dialog As InputDialog
	Dim Q As String, rtnstatus As String
	Dim CursAnchors As Cursor
	Dim whereclause As Map
	
	Q = "SELECT AnchorName, SpaceX, SpaceY, SpaceZ, EnteredDistance FROM AnchorSystems ORDER BY Weight ASC"
	CursAnchors = Starter.SQLExec.ExecQuery(Q)

	Dim coord(CursAnchors.RowCount - 1) As Coordinate
	Dim systems As List
	Dim regions As List
	systems.Initialize
	regions.Initialize
	
	For i = 0 To CursAnchors.RowCount - 1
		CursAnchors.Position = i
		dialog.InputType = dialog.INPUT_TYPE_DECIMAL_NUMBERS
		rtnstatus = dialog.Show("Distance from " & CursAnchors.GetString("AnchorName") & " System", "Locate " & CursAnchors.GetString("AnchorName").ToUpperCase, "Submit", "Cancel", "", Null)
		If rtnstatus = -3 Then
			Exit
		End If
		whereclause.Initialize
		whereclause.Put("AnchorName", CursAnchors.GetString("AnchorName"))
		DBUtils.UpdateRecord(Starter.SQLExec, "AnchorSystems", "EnteredDistance", dialog.Input, whereclause)
		coord(i).Initialize(CursAnchors.GetDouble("SpaceX"), CursAnchors.GetDouble("SpaceY"), CursAnchors.GetDouble("SpaceZ"))
		coord(i).Dist = dialog.Input
		dialog.Input = ""
		
		' Test for four or more Distances entered
		If i >= 3 Then
			systems.Clear
			regions.Clear
			' Create combinations of systems
			For aa = 0 To i
				For bb = aa + 1 To i
					For cc = bb + 1 To i
						Log("Combination: " & aa & ", " & bb & ", " & cc)
						Log("   aa: " & coord(aa).X & ", " & coord(aa).Y & ", " & coord(aa).Z & " -- Dist: " & coord(aa).Dist)
						Log("   bb: " & coord(bb).X & ", " & coord(bb).Y & ", " & coord(bb).Z & " -- Dist: " & coord(bb).Dist)
						Log("   cc: " & coord(cc).X & ", " & coord(cc).Y & ", " & coord(cc).Z & " -- Dist: " & coord(cc).Dist)
						' Test if combination has valid distances
						Dim p1p2 As Coordinate = diff(coord(bb), coord(aa))
						Dim d As Double = length(p1p2)
						Dim ex As Coordinate = scalarProd(1 / d, p1p2)
						Dim p1p3 As Coordinate = diff(coord(cc), coord(aa))
						Dim j As Double = dotProd(ex, p1p3)
						Dim ey As Coordinate = diff(p1p3, scalarProd(j, ex))
						ey = scalarProd(1 / length(ey), ey)
						Dim k As Double = dotProd(ey, diff(coord(cc), coord(aa)))
						Dim xx As Double = (coord(aa).Dist * coord(aa).Dist - coord(bb).Dist * coord(bb).Dist + d * d) / (2 * d)
						Dim yy As Double = ((coord(aa).Dist * coord(aa).Dist - coord(cc).Dist * coord(cc).Dist + j * j + k * k) / (2 * k)) - (j * xx / k)
						Dim zsq As Double = coord(aa).Dist * coord(aa).Dist - xx * xx - yy * yy
						If zsq > 0 Then
							Dim zz As Double = Sqrt(zsq)
							Dim ez As Coordinate = crossProd(ex, ey)
							Dim coord1 As Coordinate = sum(sum(coord(aa), scalarProd(xx, ex)), scalarProd(yy, ey))
							Dim coord2 As Coordinate = diff(coord1, scalarProd(zz, ez))
							coord1 = sum(coord1, scalarProd(zz, ez))
							Log("coord1: " & coord1.X & ", " & coord1.Y & ", " & coord1.Z)
							Log("coord2: " & coord2.X & ", " & coord2.Y & ", " & coord2.Z)

							' Test the distances
							Dim errorcount1 = 0, errorcount2 = 0 As Double
							errorcount1 = checkDist(coord1, coord(aa), coord(aa).Dist)
							coord1.TotSqrErr = coord1.TotSqrErr + errorcount1

							errorcount1 = checkDist(coord1, coord(bb), coord(bb).Dist)
							coord1.TotSqrErr = coord1.TotSqrErr + errorcount1

							errorcount1 = checkDist(coord1, coord(cc), coord(cc).Dist)
							coord1.TotSqrErr = coord1.TotSqrErr + errorcount1
							
							errorcount2 = checkDist(coord2, coord(aa), coord(aa).Dist)
							coord2.TotSqrErr = coord2.TotSqrErr + errorcount2
							
							errorcount2 = checkDist(coord2, coord(bb), coord(bb).Dist)
							coord2.TotSqrErr = coord2.TotSqrErr + errorcount2

							errorcount2 = checkDist(coord2, coord(cc), coord(cc).Dist)
							coord2.TotSqrErr = coord2.TotSqrErr + errorcount2
							
							Dim combination As String
							combination = aa & bb & cc

							If systems.IndexOf(combination) = -1 Then

								Dim ExistRegionFound As Boolean
								Dim r, s As Region
								r.Initialize
								s.Initialize
								Dim testregion, existingregion As Region
								testregion.Initialize
								existingregion.Initialize

								ExistRegionFound = False
								r.setAll(coord1)
								If regions.Size > 0 Then
									For w = 0 To regions.Size - 1
										existingregion = regions.Get(w)
										testregion = r.union(existingregion)
										If testregion.volume < r.volume + existingregion.volume Then
											' Add extended region to list and remove the previous region
											testregion.setSysCount(existingregion.SysCount + 1)
											regions.RemoveAt(w)
											regions.Add(testregion)
											ExistRegionFound = True
											Exit
										End If
									Next
								End If
								If ExistRegionFound = False Then
									' Add new region to the list of existing regions
									r.setSysCount(1)
									regions.Add(r)
								End If
								
								ExistRegionFound = False
								s.setAll(coord2)
								If regions.Size > 0 Then
									For w = 0 To regions.Size - 1
										existingregion = regions.Get(w)
										testregion = s.union(existingregion)
										If testregion.volume < s.volume + existingregion.volume Then
											' Add extended region to list and remove the previous region
											testregion.setSysCount(existingregion.SysCount + 1)
											regions.RemoveAt(w)
											regions.Add(testregion)
											ExistRegionFound = True
											Exit
										End If
									Next
								End If
								If ExistRegionFound = False Then
									' Add new region to the list of existing regions
									s.setSysCount(1)
									regions.Add(s)
								End If
								
								systems.Add(combination)
							End If
						End If
					Next
				Next
			Next
			
			' Test for enough matches to call an exact location match
			Dim bestcount = 0 , nextcount = 0 As Int
			Dim bestlist, nextlist As List
			Dim testregion As Region
			Dim count, matches As Int
			Dim x, y, z As Double
			
			bestlist.Initialize
			nextlist.Initialize
			
			' Remove regions where system counter is 1 or less
			Dim highregions As List
			highregions.Initialize
			
			For Each reg As Region In regions
				If reg.SysCount > 1 Then
					highregions.Add(reg)
				End If
			Next
			
			
			For w = 0 To highregions.Size - 1
				testregion = highregions.Get(w)
				For x = testregion.getminX To testregion.getmaxX Step (1 / 32.0)
					For y = testregion.getminY To testregion.getmaxY Step (1 / 32.0)
						For z = testregion.getminZ To testregion.getmaxZ Step (1 / 32.0)
							Log("x: " & x & ", y: " & y & ", z: " & z & " -- best: " & bestcount & " next: " & nextcount)
							Dim p As Coordinate
							p.Initialize(x, y, z)
							count = 0
							For cyc = 0 To i
								If coord(cyc).Dist = EDDist(p, coord(cyc), 2) Then
									count = count + 1
								End If
							Next
							matches = count
							
							If matches > bestcount Then
								nextcount = bestcount
								nextlist.Clear
								nextlist.AddAll(bestlist)
								bestcount = matches
								bestlist.Clear
								bestlist.Add(p)
							Else If matches = bestcount Then
								bestlist.Add(p)
							Else If matches > nextcount Then
								nextcount = matches
								nextlist.Clear
								nextlist.Add(p)
							Else If matches = nextcount Then
								nextlist.Add(p)	
							End If
							
							If matches > bestcount Then
								nextcount = bestcount
								nextlist.Clear
								nextlist.AddAll(bestlist)
								bestcount = matches
								bestlist.Clear
								bestlist.Add(p)
							Else If matches = bestcount Then
								Dim found = False As Boolean
								For Each coo As Coordinate In bestlist
									If (coo.X = p.X And coo.Y = p.Y And coo.Z = p.Z) Then
										found = True
										Exit
									End If
								Next
								If found = False Then
									bestlist.Add(p)
								End If
							Else If matches > nextcount Then
								nextcount = matches
								nextlist.Clear
								nextlist.Add(p)
							Else If matches = nextcount Then
								Dim found = False As Boolean
								For Each coo As Coordinate In nextlist
									If (coo.X = p.X And coo.Y = p.Y And coo.Z = p.Z) Then
										found = True
										Exit
									End If
								Next
								If found = False Then
									nextlist.Add(p)
								End If
							End If
						Next
					Next
				Next
			Next
			
			If bestcount >= 5 And (bestcount - nextcount) >= 2 Then
				Dim bestcoord As Coordinate
				bestcoord = bestlist.Get(0)
				Return bestcoord
			End If
			
		End If
	Next
	Return Null
End Sub

Sub dotProd(p1 As Coordinate, p2 As Coordinate) As Double
	' p1 and p2 are objects that have x, y, and z properties
	
	' returns the scalar (dot) product p1 . p2
	Return p1.X * p2.X + p1.Y * p2.Y + p1.Z * p2.Z
End Sub

Sub length(v As Coordinate) As Double
	' v is a vector object with x, y, and z properties
	
	' returns the length of v
	Return Sqrt(dotProd(v,v))
End Sub

Sub diff(p1 As Coordinate, p2 As Coordinate) As Coordinate
	
	' p1 and p2 are objects that have x, y, and z properties
	
	' returns the different p1 - p2 as a vector object (with x, y, z properties), calculated as single precision (as ED does)
	Dim result As Coordinate
	result.Initialize(p1.X - p2.X, p1.Y - p2.Y, p1.Z - p2.Z)
	Return result
End Sub

Sub scalarProd(s As Double, v As Coordinate) As Coordinate
	Dim result As Coordinate
	result.Initialize(s * v.X, s * v.Y, s * v.Z)
	Return result
End Sub

Sub crossProd(p1 As Coordinate, p2 As Coordinate) As Coordinate
	Dim result As Coordinate
	result.Initialize(p1.Y * p2.Z - p1.Z * p2.Y, p1.Z * p2.X - p1.X * p2.Z, p1.X * p2.Y - p1.Y * p2.X)
	Return result
End Sub

Sub sum(p1 As Coordinate, p2 As Coordinate) As Coordinate
	Dim result As Coordinate
	result.Initialize(p1.X + p2.X, p1.Y + p2.Y, p1.Z + p2.Z)
	Return result
End Sub

Sub checkDist(p1 As Coordinate, p2 As Coordinate, dist As Double) As Double
	Dim dp As Int = 2
	Dim v As Coordinate = diff(p2, p1)
	Dim e As Float = fround(Sqrt(fround(fround(fround(v.X * v.X) + fround(v.Y * v.Y)) + fround(v.Z * v.Z))))
	Dim resultdist As Double = Round2(e, dp)
	Dim error As Double = Abs(resultdist - dist)
	Return error * error
End Sub

Sub EDDist(p1 As Coordinate, p2 As Coordinate, dp As Int) As Double
	Dim v As Coordinate = diff(p2, p1)
	Dim d As Float = fround(Sqrt(fround(fround(fround(v.X * v.X) + fround(v.Y * v.Y)) + fround(v.Z * v.Z))))
	Dim resultdist As Double = Round2(d, dp)
	Return resultdist
End Sub

Sub fround(d As Double) As Float
	Dim f As Float
	f = d
	Return f
End Sub

#End Region

#Region Economy

Sub FindEconomy(IndexNum As Int) As String
	Dim I As Int
	Dim Query As String
	Dim result As String
	Dim CursEconomyList As Cursor
	Dim SQLExec As SQL
	' Open connection to Database
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)
	
	result = ""
	Query = "SELECT * FROM Economies ORDER BY EconomyID ASC"
	CursEconomyList = SQLExec.ExecQuery(Query)
	If CursEconomyList.RowCount > 0 Then
		For I = 0 To CursEconomyList.RowCount - 1
			CursEconomyList.Position = I
			If IndexNum = CursEconomyList.GetInt("EconomyID") Then
				result = CursEconomyList.GetString("EconomyDesc")
				Exit
			End If
		Next
	End If
	CursEconomyList.Close
	Return result
	
End Sub

Sub FindEconomyNames(EconInt As Int) As String
	Dim result As StringBuilder
	result.Initialize
	If EconInt > 524287 Then
		result.Append(FindEconomy(524288))
		EconInt = EconInt - 524288
	End If
	If EconInt > 262143 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(262144))
		EconInt = EconInt - 262144
	End If
	If EconInt > 131071 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(131072))
		EconInt = EconInt - 131072
	End If
	If EconInt > 65535 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(65536))
		EconInt = EconInt - 65536
	End If
	If EconInt > 32767 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(32768))
		EconInt = EconInt - 32768
	End If
	If EconInt > 16383 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(16384))
		EconInt = EconInt - 16384
	End If
	If EconInt > 8191 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(8192))
		EconInt = EconInt - 8192
	End If
	If EconInt > 4095 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(4096))
		EconInt = EconInt - 4096
	End If
	If EconInt > 2047 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(2048))
		EconInt = EconInt - 2048
	End If
	If EconInt > 1023 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(1024))
		EconInt = EconInt - 1024
	End If
	If EconInt > 511 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(512))
		EconInt = EconInt - 512
	End If
	If EconInt > 255 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(256))
		EconInt = EconInt - 256
	End If
	If EconInt > 127 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(128))
		EconInt = EconInt - 128
	End If
	If EconInt > 63 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(64))
		EconInt = EconInt - 64
	End If
	If EconInt > 31 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(32))
		EconInt = EconInt - 32
	End If
	If EconInt > 15 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(16))
		EconInt = EconInt - 16
	End If
	If EconInt > 7 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(8))
		EconInt = EconInt - 8
	End If
	If EconInt > 3 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(4))
		EconInt = EconInt - 4
	End If
	If EconInt > 1 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(2))
		EconInt = EconInt - 2
	End If
	If EconInt > 0 Then
		If result.Length > 0 Then
			result.Append(", ")
		End If
		result.Append(FindEconomy(1))
		EconInt = EconInt - 1
	End If
	Return result
End Sub

Sub FindEconomyNum(EconomyName As String) As Int
	Dim I As Int
	Dim Query As String
	Dim result As Int
	Dim CursEconomyList As Cursor
	Dim SQLExec As SQL
	' Open connection to Database
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)
	
	result = 0
	Query = "SELECT * FROM Economies ORDER BY EconomyID ASC"
	CursEconomyList = SQLExec.ExecQuery(Query)
	If CursEconomyList.RowCount > 0 Then
		For I = 0 To CursEconomyList.RowCount - 1
			CursEconomyList.Position = I
			If EconomyName = CursEconomyList.GetString("EconomyDesc") Then
				result = CursEconomyList.GetInt("EconomyID")
				Exit
			End If
		Next
	End If
	CursEconomyList.Close
	Return result
	
End Sub

#End Region

#Region Systems

Sub SystemExists(SysName As String) As Boolean
	Dim Q As String
	Dim CursSystems As Cursor
	Dim result As Boolean

	'Checks if the System Name already exists
	Q = "SELECT SystemName FROM Systems WHERE SystemName = ?"
	CursSystems = Starter.SQLExec.ExecQuery2(Q, Array As String(SysName))
	If CursSystems.RowCount > 0 Then
		Msgbox("This Star System already exists", "A T T E N T I O N")
		result = True
	Else
		result = False
	End If
	CursSystems.Close
	Return result
	
End Sub

#End Region


