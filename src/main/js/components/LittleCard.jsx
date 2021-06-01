import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';


const useStyles = makeStyles({
  root: {
    minWidth: 150,
    marginBottom: 10
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)',
  },
  idData: {
    fontSize: 12,
    textOverflow: 'ellipsis',
    overflow: 'hidden',
    whiteSpace: 'nowrap',
  },
  title: {
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden'
  },
  text: {
    marginBottom: 12,
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    overflow: 'hidden'
  }
});

export default function LittleCard(props) {
  const classes = useStyles();
  const bull = <span className={classes.bullet}>•</span>;

  return (
    <Card className={classes.root}>
      <CardContent>
        <Typography className={classes.idData} color="textSecondary" gutterBottom>
          {props.card.id}
        </Typography>
        <Typography className={classes.title} variant="h5" component="h2">
          {props.card.title}
        </Typography>
        <Typography className={classes.text} color="textSecondary">
          {props.card.text}
        </Typography>
        <Typography variant="body2" component="p">
          Owner
        </Typography>
      </CardContent>
    </Card>
  );
}