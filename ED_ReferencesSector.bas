Type=Class
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Class module
Sub Class_Globals
	Dim WidthAngle As Double
	Dim AzimuthStartRad As Double
	Dim LatitudeStartRad As Double
	
	Private usedReferences As List
	Private candidateReferences As List
	Private optcandidateReferences As List
	
	Private minWeight As Double
	
	Dim m As Math
	Dim refsystem As ED_ReferenceSystem
	
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize(az As Double, alt As Double, wid As Int)
	
	AzimuthStartRad = az * m.PI / 180
	LatitudeStartRad = alt * m.PI / 180
	WidthAngle = wid

	usedReferences.Add(refsystem)
	candidateReferences.Add(refsystem)
	optcandidateReferences.Add(refsystem)
	minWeight = m.DoubleMaxValue
	
End Sub

Public Sub Azimuth As Double
	Return AzimuthStartRad * 180 / m.PI
End Sub

Public Sub Latitude As Double
	Return LatitudeStartRad * 180 / m.PI
End Sub

Public Sub AzimuthCenter As Double
	Return AzimuthStartRad * 180 / m.PI + WidthAngle / 2
End Sub

Public Sub LatitudeCenter As Double
	Return LatitudeStartRad * 180 / m.PI + WidthAngle / 2
End Sub

Public Sub AzimuthCenterRad As Double
	Return AzimuthCenter * m.PI / 180
End Sub

Public Sub LatitudeCenterRad As Double
	Return LatitudeCenter * m.PI / 180
End Sub

Public Sub ReferencesCount As Int
	Return usedReferences.Size
End Sub

Public Sub CandidatesCount As Int
	Return candidateReferences.Size - usedReferences.Size
End Sub

Public Sub Width As Double
	Return WidthAngle * m.PI / 180
End Sub

Public Sub Name As String
	Return "Sector:" + (AzimuthStartRad * 180 / m.PI) + ":" + (LatitudeStartRad * 180 / m.PI)
End Sub