import React, { useState, useEffect, useContext } from 'react'
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import LittleCard from './LittleCard.jsx'


const useStyles = makeStyles((theme) => ({
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column',
  },
  fixedHeight: {
    height: 800,
  },
  fixedWidth: {
    width: 300,
  }
}));


export default function Column(props) {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight, classes.fixedWidth);

  const cards = props.column.cards.map(card => <LittleCard card={card} key={card.id} />)

  return (
    // <div className={classes.appBarSpacer} />
    <Container maxWidth="lg" className={classes.container}>
      <Grid container>
        {/* Columns */}
        <Grid item>
          <Paper className={fixedHeightPaper}>
            <div><span>{props.column.title}({props.column.id})</span></div>
            <div>{cards}</div>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  )
}