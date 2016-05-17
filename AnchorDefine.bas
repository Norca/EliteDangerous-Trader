Type=Service
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Service_Create

End Sub

Sub Service_Start (StartingIntent As Intent)
	' Disable the Anchors Flag
	Starter.AnchorsDefined = False
	
	Dim i As Int

	'Clear System Plot Temp Tables
	EDTables.SystemPlotTableDelete
	'Create System Plot Temp Tables
	EDTables.TempTables
	
	' Creates Sectors in space around the current selected System
	CreateSectors

	' Adds known system locations to sectors
	AddSystemsToSectors

	' Finds the best systems to triangulate
	For i = 0 To 15 ' was 15
		GetCandidate
	Next

	' Adds location points to anchor systems
	UpdateAnchors ' Adds the X,Y,Z to the identified Anchor Systems from the Systems Table

	Dim Q As String
	Dim CursAnchors As Cursor
	Q = "SELECT * FROM AnchorSystems"
	CursAnchors = Starter.SQLExec.ExecQuery(Q)
	If CursAnchors.RowCount < 5 Then
		' Disable the Anchors Flag
		Starter.AnchorsDefined = False
	Else
		' Enable the Anchors Flag
		Starter.AnchorsDefined = True
	End If
	Starter.AnchorsNumber = CursAnchors.RowCount
End Sub

Sub Service_Destroy

End Sub


Sub CreateSectors
	Dim record As Map

		' Add Static Data
		Dim m As Math
		Dim az, aznr, alt, altnr, sections As Int
		Dim record As Map
		sections = 12
		az = -180
		aznr = 0
		alt = -90
		altnr = 0
		m.Initialize
		
		Do While az < 180
			Do While alt < 90
				record.Initialize
				
				record.Put("Sectorname", "Sector:" & aznr & ":" & altnr)
				record.Put("AzimuthStartRad", az * m.PI / 180)
				record.Put("LatitudeStartRad", alt * m.PI / 180)
				record.Put("WidthAngle", 360 / sections)
				record.Put("Azimuth", az)
				record.Put("AzimuthCenter", az + (360 / sections) / 2)
				record.Put("AzimuthCenterRad", (az + (360 / sections) / 2) * m.PI / 180)
				record.Put("Latitude", alt)
				record.Put("LatitudeCenter", alt + (360 / sections) / 2)
				record.Put("LatitudeCenterRad", (alt + (360 / sections) / 2) * m.PI / 180)
				record.Put("Width", (360 / sections) * m.PI / 180)
				record.Put("minWeight", m.DoubleMaxValue)
				SQLUtils.Table_InsertMap(Starter.SQLExec,"ReferenceSector",record)
		
				alt = alt + (360 / sections)
				altnr = altnr + 1
			Loop
			alt = -90 'Reset inner loop value
			altnr = 0 'Reset inner loop counter
			
			az = az + (360 / sections)
			aznr = aznr + 1
		Loop
End Sub

Sub AddSystemsToSectors
	Dim Q As String
	Dim CursEstimated, CursSystems As Cursor
	Dim EstiX, EstiY, EstiZ As Double
	Dim I As Int
	Dim Distance, Azimuth, Altitude As Double
	Dim aznr, altnr, sections As Int
	Dim m As Math
	
	sections = 6 ' was 12
	aznr = 0
	altnr = 0
	m.Initialize
	
	'Set EstimatedPosition from Last Known system
	Q = "SELECT * FROM Systems WHERE SystemName = ?"
	CursEstimated = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursEstimated.RowCount > 0 Then
		CursEstimated.Position = 0
		EstiX = CursEstimated.GetDouble("SpaceX")
		EstiY = CursEstimated.GetDouble("SpaceY")
		EstiZ = CursEstimated.GetDouble("SpaceZ")
	Else
		Starter.CurrLocation = "Sol"
		EstiX = 0.00
		EstiY = 0.00
		EstiZ = 0.00		
	End If
	
	'Get list of Systems that have known exact location
	Q = "SELECT * FROM Systems WHERE ExactLocation = 1"
	CursSystems = Starter.SQLExec.ExecQuery(Q)
	CursSystems.Position = 0
	For I = 0 To CursSystems.RowCount - 1
		CursSystems.Position = I
		Distance = Elite.DistanceBetween(CursSystems.GetString("SystemName"),Starter.CurrLocation)
		Azimuth = ATan2((CursSystems.GetDouble("SpaceY") - EstiY), (CursSystems.GetDouble("SpaceX") - EstiX))
		Altitude = ACos((CursSystems.GetDouble("SpaceZ") - EstiZ) / Distance)
		
		If Distance > 0 Then
			aznr = Floor((Azimuth * 180 / m.PI + 180) / (360 / sections))
			altnr = Floor((Altitude * 180 / m.PI) / (360 / sections))
			
			AddCandidate("Sector:" & aznr & ":" & altnr, CursSystems.GetString("SystemName"), Distance)
		End If
	Next
	CursEstimated.Close
	CursSystems.Close
End Sub

Sub AddCandidate(Sector As String, System2Add As String, Distance As Double)
	Dim Q As String
	Dim CursSector, Cursoptcand As Cursor
	Dim Weight, minWeight As Double
	Dim optcandCount As Int
	Dim record, whereclause As Map
	
	Q = "SELECT * FROM ReferenceSector WHERE SectorName = ?"
	CursSector = Starter.SQLExec.ExecQuery2(Q, Array As String(Sector))
	CursSector.Position = 0
	minWeight = CursSector.GetDouble("minWeight")

	Weight = SystemWeighting(System2Add, Distance)
	
	'Add System to Sector as candidate Reference	
	record.Initialize
	record.Put("SectorName", Sector)
	record.Put("SystemName", System2Add)
	record.Put("Weight", Weight)
	SQLUtils.Table_InsertMap(Starter.SQLExec,"candidateReferences",record)
	
	'Count the optcandidateReferences
	Q = "SELECT * FROM optcandidateReferences"
	Cursoptcand = Starter.SQLExec.ExecQuery(Q)
	optcandCount = Cursoptcand.RowCount
	
	If Weight < minWeight Then
		' Update the minWeight of the Sector
		whereclause.Initialize
		whereclause.Put("SectorName", Sector)
		DBUtils.UpdateRecord(Starter.SQLExec, "ReferenceSector", "minWeight", Weight, whereclause)
		' Add System to Sector as optcandidate Reference
		record.Initialize
		record.Put("SectorName", Sector)
		record.Put("SystemName", System2Add)
		record.Put("Weight", Weight)
		SQLUtils.Table_InsertMap(Starter.SQLExec,"optcandidateReferences",record)
	Else If optcandCount < 10 Then
		' Add system to Sector as optcandidate Reference
		record.Initialize
		record.Put("SectorName", Sector)
		record.Put("SystemName", System2Add)
		record.Put("Weight", Weight)
		SQLUtils.Table_InsertMap(Starter.SQLExec,"optcandidateReferences",record)
	Else If optcandCount < 100 And Distance < 1000 And Distance > 100 Then
		' Add system to Sector and optcandidate Reference
		record.Initialize
		record.Put("SectorName", Sector)
		record.Put("SystemName", System2Add)
		record.Put("Weight", Weight)
		SQLUtils.Table_InsertMap(Starter.SQLExec,"optcandidateReferences",record)
	End If
	CursSector.Close
	Cursoptcand.Close
End Sub

Sub SystemWeighting(System2Add As String, Distance As Double) As Double
	Dim Modifier As Int
	
	Modifier = 0
	
	If Regex.IsMatch("\s[A-Z][A-Z].[A-Z]\s", System2Add) = True Then
		Modifier = Modifier + 20
	End If
	If Distance > 20000 Then
		Modifier = Modifier + 10
	End If
	If Distance > 30000 Then
		Modifier = Modifier + 20
	End If
	
	Return System2Add.Length * 2 + Sqrt(Distance) / 3.5 + Modifier

End Sub

Sub GetCandidate
	Dim Q As String
	Dim CursSectors, CursRefer, CursCandi, CursSectorLoop, CursCandiLoop As Cursor
	Dim i, j As Int
	Dim Dist, minDist, maxDist As Double
	Dim sectorcandidate As String
	
	maxDist = 0
	Q = "SELECT * FROM ReferenceSector"
	CursSectorLoop = Starter.SQLExec.ExecQuery(Q)
	CursSectors = Starter.SQLExec.ExecQuery(Q)
	CursSectors.Position = 0

	For i = 0 To CursSectors.RowCount - 1
		CursSectors.Position = i

		Q = "SELECT * FROM usedReferences WHERE SectorName = '" & CursSectors.GetString("SectorName") & "'"
		CursRefer = Starter.SQLExec.ExecQuery(Q)

		Q = "SELECT * FROM candidateReferences WHERE SectorName = '" & CursSectors.GetString("SectorName") & "'"
		CursCandi = Starter.SQLExec.ExecQuery(Q)
		
		If CursRefer.RowCount = 0 And CursCandi.RowCount > 0 Then
			Dist = 0
			minDist = 10

			For j = 0 To CursSectorLoop.RowCount - 1
				CursSectorLoop.Position = j

				Q = "SELECT * FROM candidateReferences WHERE SectorName = '" & CursSectorLoop.GetString("SectorName") & "'"
				CursCandiLoop = Starter.SQLExec.ExecQuery(Q)

				If CursCandi.RowCount > 0 Then
					'Get Distance Calculation
					Dist = CalculateAngularDistance(CursSectors.GetDouble("AzimuthCenterRad"),CursSectors.GetDouble("LatitudeCenterRad"),CursSectorLoop.GetDouble("AzimuthCenterRad"),CursSectorLoop.GetDouble("LatitudeCenterRad"))

					If Dist > 0.001 Then

						If Dist < minDist Then
							minDist = Dist
						End If

					End If

				End If
				CursCandiLoop.Close

			Next

			If minDist > maxDist Then
				maxDist = minDist
				sectorcandidate = CursSectors.GetString("SectorName")
			End If

		End If
		CursRefer.Close
		CursCandi.Close
		
	Next
	GetBestCandidate(sectorcandidate)
	CursSectorLoop.Close
	CursSectors.Close
End Sub

Sub GetBestCandidate(sector As String)
	Dim Q As String
	Dim CursOptCand As Cursor
	Dim record As Map
	Q = "SELECT * FROM optcandidateReferences WHERE SectorName = ? ORDER BY Weight ASC"
	CursOptCand = Starter.SQLExec.ExecQuery2(Q, Array As String(sector))
	If CursOptCand.RowCount > 0 Then
		CursOptCand.Position = 0
	
		'Add system to usedReferences Table
		record.Initialize
		record.Put("SectorName", CursOptCand.GetString("SectorName"))
		record.Put("SystemName", CursOptCand.GetString("SystemName"))
		record.Put("Weight", CursOptCand.GetString("Weight"))
		SQLUtils.Table_InsertMap(Starter.SQLExec, "usedReferences", record)

		record.Initialize
		record.Put("AnchorName", CursOptCand.GetString("SystemName"))
		record.Put("Weight", CursOptCand.GetString("Weight"))
		SQLUtils.Table_InsertMap(Starter.SQLExec, "AnchorSystems", record)

	End If	
	CursOptCand.Close
End Sub

Sub CalculateAngularDistance(Long1Rad As Double, Lati1Rad As Double, Long2Rad As Double, Lati2Rad As Double) As Double
	Dim LongitudeDiff, AngleCalculation As Double
	Dim m As Math
	m.Initialize
	
	LongitudeDiff = Abs(Long1Rad - Long2Rad)
	
	If LongitudeDiff > m.PI Then
		LongitudeDiff = 2.0 * m.PI - LongitudeDiff
	End If
	
	AngleCalculation = ACos(Sin(Lati1Rad) * Sin(Lati2Rad) + Cos(Lati1Rad) * Cos(Lati2Rad) * Cos(LongitudeDiff))
	
	Return AngleCalculation
	
End Sub

Sub UpdateAnchors
	Dim Q As String
	Dim CursAnchors, CursSystems As Cursor
	Dim whereclause As Map
	Q = "SELECT * FROM AnchorSystems"
	CursAnchors = Starter.SQLExec.ExecQuery(Q)
	CursAnchors.Position = 0
	For i = 0 To CursAnchors.RowCount - 1
		CursAnchors.Position = i
		Q = "SELECT * FROM Systems WHERE SystemName = ?"
		CursSystems = Starter.SQLExec.ExecQuery2(Q, Array As String(CursAnchors.GetString("AnchorName")))
		CursSystems.Position = 0
		whereclause.Initialize
		whereclause.Put("AnchorName", CursSystems.GetString("SystemName"))
		DBUtils.UpdateRecord(Starter.SQLExec, "AnchorSystems", "SpaceX", CursSystems.GetDouble("SpaceX"), whereclause)
		DBUtils.UpdateRecord(Starter.SQLExec, "AnchorSystems", "SpaceY", CursSystems.GetDouble("SpaceY"), whereclause)
		DBUtils.UpdateRecord(Starter.SQLExec, "AnchorSystems", "SpaceZ", CursSystems.GetDouble("SpaceZ"), whereclause)
	Next
	CursAnchors.Close
	CursSystems.Close
End Sub