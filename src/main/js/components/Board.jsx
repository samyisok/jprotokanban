import React, { useState, useEffect, useContext } from 'react'
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Column from './Column.jsx'


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
    height: 940,
  },
  fixedWidth: {
    width: 1100,
  },
  columnContainer: {
    display: 'flex',
    flexDirection: 'row',
    flexWrap: 'nowrap',
    justifContent: 'center',
  }

}));


export default function Board(props) {
  const classes = useStyles();
  const fixedHeightPaper = clsx(classes.paper, classes.fixedHeight, classes.fixedWidth);

  const columns = props.board.columns.map((column) => <Column column={column} key={column.id} />)

  return (
    // <div className={classes.appBarSpacer} />
    <Container maxWidth="lg" className={classes.container}>
      <Grid container>
        {/* Columns */}
        <Grid item>
          <Paper className={fixedHeightPaper}>
            <div><span>{props.board.title}</span></div>
            <div className={classes.columnContainer}>{columns}</div>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  )
}