import React, { useState } from 'react';

import { makeStyles } from '@material-ui/core/styles';

import { Button } from '@material-ui/core';

import { Document, Page, pdfjs } from 'react-pdf';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';

import pdfFile from '../assets/trial.pdf'; 
pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;

// const PdfLoader = () => {
//   const [numPages, setNumPages] = useState(null);
//   const [pageNumber, setPageNumber] = useState(3);
 
//   const onDocumentLoadSuccess = (proba) => {
//     const {numPages} = proba
//     setNumPages(numPages);
//   }

//   const onDocumentLoadError = (error) => {
//     console.log(error);
//   }
 
//   return (
//     <div>
//       <Document
//         file={pdfFile}
//         onLoadSuccess={onDocumentLoadSuccess}
//         onLoadError={onDocumentLoadError}
//       >
//         {/* <Page pageNumber={pageNumber} /> */}
//         {Array.from(
//         new Array(numPages),
//         (el, index) => (
//           <Page
//             key={`page_${index + 1}`}
//             pageNumber={index + 1}
//           />
//         ),
//       )}
//       </Document>
//       <p>Page {pageNumber} of {numPages}</p>
//     </div>
//   );
// }

const useStyles = makeStyles((theme) => ({
    button: {
      margin: theme.spacing(1),
    },
  }));

const PdfLoader = () => {
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
          file={pdfFile}
          onLoadSuccess={onDocumentLoadSuccess}
        >
          <Page pageNumber={pageNumber} />
        </Document>
        <div>
          <p>
            Page {pageNumber || (numPages ? 1 : '--')} of {numPages || '--'}
          </p>
          <Button
            className={classes.button}
            disabled={pageNumber <= 1}
            onClick={previousPage}
            color="secondary"
            variant="contained"
            startIcon={<ChevronLeftIcon />}
          >
            Previous
          </Button>
          <Button
            className={classes.button}
            disabled={pageNumber >= numPages}
            onClick={nextPage}
            color="primary"
            variant="contained"
            endIcon={<ChevronRightIcon />}
          >
            Next
          </Button>
        </div>
      </>
    );
  }

export default PdfLoader;