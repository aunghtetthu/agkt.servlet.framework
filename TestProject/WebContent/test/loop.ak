
<div>#agkt{helloMessage}</div>

<table class="table">
	#akStartLoop{peopleList}
		<tr>
			<td> #akLoopItem{peopleList[name]}</td>
			<td> #akLoopItem{peopleList[age]}</td>
			<td> #akLoopItem{peopleList[address]}</td>
			#if(#akLoopItem{peopleList[staff]})
				<td>Is Staff</td>
			#else
				<td>Not Staff</td>
			#endIf
			
		</tr>
	#akEndLoop{peopleList}
</table>

