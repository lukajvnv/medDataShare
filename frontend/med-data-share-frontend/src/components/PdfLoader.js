import React, { useState } from 'react';

import { makeStyles } from '@material-ui/core/styles';

import { Button } from '@material-ui/core';

import { Document, Page, pdfjs } from 'react-pdf';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import strings from '../localization';

pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


const useStyles = makeStyles((theme) => ({
    button: {
      margin: theme.spacing(1),
    },
  }));

const PdfLoader = ({
  pdf
}) => {
    const classes = useStyles();

    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
  
    function onDocumentLoadSuccess({ numPages }) {
      setNumPages(numPages);
      setPageNumber(1);
    }
  
    function changePage(offset) {
      setPageNumber(prevPageNumber => prevPageNumber + offset);
    }
  
    function previousPage() {
      changePage(-1);
    }
  
    function nextPage() {
      changePage(1);
    }
  
    return (
      <>
        <Document
          file={pdf}
          onLoadSuccess={onDocumentLoadSuccess}
        >
          <Page pageNumber={pageNumber} />
        </Document>
        <div>
          <p>
            {strings.pdf.page} {pageNumber || (numPages ? 1 : '--')} of {numPages || '--'}
          </p>
          <Button
            className={classes.button}
            disabled={pageNumber <= 1}
            onClick={previousPage}
            color="secondary"
            variant="contained"
            startIcon={<ChevronLeftIcon />}
          >
            {strings.pdf.previous}
          </Button>
          <Button
            className={classes.button}
            disabled={pageNumber >= numPages}
            onClick={nextPage}
            color="primary"
            variant="contained"
            endIcon={<ChevronRightIcon />}
          >
            {strings.pdf.next}
          </Button>
        </div>
      </>
    );
  }

export default PdfLoader;