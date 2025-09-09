package com.dntks.groupstagesimulator.ui.groupstatistics.view

/*
@Composable
fun TournamentGroupScreen(
    groupTitle: String = "Group A",
    standings: List<Standing>,
    rounds: List<Round>,
    onDelete: (() -> Unit)? = null
) {
    Surface(color = Cream) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                // Top bar mimic (simple)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        groupTitle,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.weight(1f))
                    if (onDelete != null) {
                        TextButton(onClick = onDelete) { Text("Delete data") }
                    }
                }
            }

            item { StandingsCard(standings) }

            items(rounds) { round -> RoundCard(round) }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}
@Composable
private fun StandingsCard(rows: List<Standing>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DeepGreen)
            .padding(12.dp)
    ) {
        // Header band
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(DeepGreen90)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HCell("#", .7f); HCell("Team", 2.2f)
            listOf("P","W","D","L","GF","GA","GD","Pts").forEach { HCell(it, .85f, center = true) }
        }

        Spacer(Modifier.height(6.dp))

        rows.forEach { s ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CCell(s.pos.toString(), .7f, center = true, bold = true)
                CCell(s.team, 2.2f, maxLines = 1)
                CCell(s.p.toString(), .85f, center = true)
                CCell(s.w.toString(), .85f, center = true)
                CCell(s.d.toString(), .85f, center = true)
                CCell(s.l.toString(), .85f, center = true)
                CCell(s.gf.toString(), .85f, center = true)
                CCell(s.ga.toString(), .85f, center = true)
                CCell(if (s.gd >= 0) "+${s.gd}" else s.gd.toString(), .85f, center = true)
                CCell(s.pts.toString(), .85f, center = true, bold = true)
            }
        }
    }
}
@Composable
private fun RowScope.HCell(
    text: String, weight: Float, center: Boolean = false
) = Text(
    text,
    color = Color.White.copy(alpha = 0.92f),
    fontSize = 12.sp,
    fontWeight = FontWeight.SemiBold,
    textAlign = if (center) TextAlign.Center else TextAlign.Start,
    modifier = Modifier.weight(weight),
    maxLines = 1,
    overflow = TextOverflow.Clip
)


@Composable
private fun RowScope.CCell(
    text: String, weight: Float, center: Boolean = false, bold: Boolean = false, maxLines: Int = 1
) = Text(
    text,
    color = Color.White,
    fontSize = 13.sp,
    fontWeight = if (bold) FontWeight.SemiBold else FontWeight.Normal,
    textAlign = if (center) TextAlign.Center else TextAlign.Start,
    modifier = Modifier.weight(weight),
    maxLines = maxLines,
    overflow = TextOverflow.Ellipsis
)
@Composable
private fun RoundCard(round: Round) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(CardBeige)
            .padding(16.dp)
    ) {
        Text(
            round.title,
            color = DeepGreen,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(10.dp))

        MatchHeader()
        Spacer(Modifier.height(6.dp))

        round.matches.forEachIndexed { i, m ->
            val bg = if (i % 2 == 1) Color.White.copy(alpha = 0.5f) else Color.Transparent
            MatchRow(m, bg)
        }
    }
}

@Composable
private fun MatchHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.65f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Home", color = DeepGreen, modifier = Modifier.weight(2.2f))
        Text("Score", color = DeepGreen, textAlign = TextAlign.Center, modifier = Modifier.weight(1.2f))
        Text("Away", color = DeepGreen, textAlign = TextAlign.End, modifier = Modifier.weight(2.2f))
    }
}

@Composable
private fun MatchRow(m: Match, background: Color) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(m.home, color = DeepGreen, modifier = Modifier.weight(2.2f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("${m.hs} – ${m.as_}", color = DeepGreen, textAlign = TextAlign.Center,
            modifier = Modifier.weight(1.2f))
        Text(m.away, color = DeepGreen, textAlign = TextAlign.End,
            modifier = Modifier.weight(2.2f), maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}*/