import React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import { dateToString } from '../../../util/DateUtil';
import { cyan } from '@material-ui/core/colors';
import {getError, hasError} from "../../../functions/Validation";

import strings from "../../../localization";

import rtg from '../../../assets/rtg.jpg';
import pdfFile from '../../../assets/trial.pdf'; 

import {
    Button, Paper, Slide,
    Typography, Box, 
    Accordion, AccordionDetails, AccordionSummary,
    Dialog, DialogActions, DialogTitle, DialogContent,
    InputLabel, Select, FormHelperText, FormControl
} from "@material-ui/core";

import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ImportExportIcon from '@material-ui/icons/ImportExport';

import PdfLoader from '../../../components/PdfLoader';
import AccessType from '../../../constants/AccessType';

import { getClinicalTrialInPdf, getClinicalTrialImage } from '../../../services/UserService';

const accessTypeDatasource = [
    {key: AccessType.FORBIDDEN, label: 'For private use only'},
    {key: AccessType.UNCONDITIONAL, label: 'Unlimited access'},
    {key: AccessType.ASK_FOR_ACCESS, label: 'Ask for access'}
];

const useStyles = makeStyles((theme) => ({
    orange: {
      color: theme.palette.getContrastText(cyan[500]),
      backgroundColor: cyan[500],
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        flexBasis: '33.33%',
        flexShrink: 0,
        backgroundColor: cyan[500],
      },
      secondaryHeading: {
        fontSize: theme.typography.pxToRem(15),
        color: theme.palette.text.secondary,
        backgroundColor: cyan[500],
      },
  }));

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="right" ref={ref} {...props} />;
});

const transformationFunctions = {
    formatString: (value) => dateToString(value)
}

const renderClinicalTrialAttribute = (clinicalTrialAttributeMeta, clinicalTrial, styleClass) => {
    const key = clinicalTrialAttributeMeta.key;
    const label = clinicalTrialAttributeMeta.label;
    let value = clinicalTrial[key];

    const transformation = clinicalTrialAttributeMeta.transform;
    if(transformation){
        value = transformationFunctions[transformation](value);
    } 
   
    return <Box key={key} mb={1}>
        <Paper className="paper">
            <Typography gutterBottom variant="h5" component="h2" color="primary" className={styleClass.orange}>
                {label}
            </Typography>
            <Typography variant="body2" color="primary" component="p">
                {value}
            </Typography>
        </Paper>
    </Box>
}

const ClinicalTrialDetail = ({
    shouldOpen,
    clinicalTrial,
    onClose,
    onSubmit,
    onChange,
    data,
    attributesDescription,
    form,
    errors,
    submitLabel,
    displayResource
}) => {
    const classes = useStyles();
    const [imageExpanded, setImageExpanded] = React.useState(false);
    const [pdfExpanded, setPdfExpanded] = React.useState(false);
    const [img, setImg] = React.useState(undefined);

    const exportPdf = () => {
        getClinicalTrialInPdf(clinicalTrial.id)
        .then(response => {
            if(!response.ok){
                return;
            }
            const file = new Blob(
                [response.data], 
                {type: 'application/pdf'}
            );
            const fileURL = URL.createObjectURL(file);
            window.open(fileURL);
        })
        .catch(error => {
            
        });
    }

    const displayImageAction = (shouldDisplay) => {
        if(!img && shouldDisplay) {
            getClinicalTrialImage(clinicalTrial.resourcePath)
            .then(response => {
                const file = new Blob(
                    [response.data], 
                    {type: 'image/png'});
                  const fileURL = URL.createObjectURL(file);
                  setImg(fileURL);
                  setImageExpanded(shouldDisplay);

            })
            .catch(error => {
                console.log(error);
            });
        } else {
            setImageExpanded(shouldDisplay);
        }
    }

    const imageSrc = img ? img : rtg;

    return (
        <Dialog
            fullWidth
            maxWidth = 'md'
            open={shouldOpen}
            onClose={onClose}
            // fullScreen
            aria-labelledby="scroll-dialog-title"
            aria-describedby="scroll-dialog-description"
            TransitionComponent={Transition}
            disableBackdropClick={form}
            disableEscapeKeyDown={form}
        >
            <DialogTitle id="scroll-dialog-title">{strings.clinicalTrial.detail.pageTitle}</DialogTitle>
            <DialogContent
                // className={classes.dialog}
                dividers
            >
            {
                form && <Box mb={1}>
                    <Paper className="paper">
                        <Typography gutterBottom variant="h5" component="h2" color="primary" className={classes.orange}>
                            {strings.clinicalTrial.form.clinicalTrialType}
                        </Typography>
                        <FormControl 
                            className="formControl"
                            required
                            error={hasError(errors, 'accessType')}
                        >
                            <InputLabel id="accessTypeId">{strings.clinicalTrial.form.clinicalTrialType}</InputLabel>
                            <Select
                                labelId="accessTypeId"
                                onChange={onChange}
                                value={data.accessType || ''}
                                native
                                inputProps={{
                                    name: "accessType",
                                }}
                            >
                                <option aria-label="None" value="" />
                                {
                                    accessTypeDatasource.map((accessType) => (
                                        <option key={accessType.key} value={accessType.key}>{accessType.label}</option>
                                    ))
                                }
                            </Select>
                            <FormHelperText >{getError(errors, 'accessType')}</FormHelperText>
                        </FormControl>
                    </Paper>
                </Box>
            }
            {
                attributesDescription.map(attribute => renderClinicalTrialAttribute(attribute, clinicalTrial, classes))
            }
            {
                displayResource && <Box mb={1}>
                    <Accordion expanded={imageExpanded} onChange={() => displayImageAction(!imageExpanded)}>
                        <AccordionSummary
                            expandIcon={<ExpandMoreIcon />}
                            aria-controls="panel1bh-content"
                            id="panel1bh-header"
                        >
                            <Typography 
                                className={classes.heading}
                            >
                                {strings.clinicalTrial.detail.image}
                            </Typography> 
                            <Typography 
                                className={classes.secondaryHeading}
                            >
                                I am an accordion
                            </Typography> 
                        </AccordionSummary>
                        <AccordionDetails>
                        <img 
                            src={imageSrc} 
                            alt="jfdl" 
                            height={500} 
                            style={{
                                maxWidth:'100%'
                            }}
                        />
                        </AccordionDetails>
                    </Accordion>
                </Box>
            }
            {
                displayResource && <Box mb={1}>
                                    <Accordion expanded={pdfExpanded} onChange={() => setPdfExpanded(!pdfExpanded)}>
                                        <AccordionSummary
                                            expandIcon={<ExpandMoreIcon />}
                                            aria-controls="panel1bh-content"
                                            id="panel1bh-header"
                                        >
                                            <Typography 
                                                className={classes.heading}
                                            >
                                                {strings.clinicalTrial.detail.pdf}
                                            </Typography> 
                                            <Typography 
                                                className={classes.secondaryHeading}
                                            >
                                                I am an accordion
                                            </Typography> 
                                        </AccordionSummary>
                                        <AccordionDetails>
                                            <PdfLoader pdf={pdfFile} />
                                        </AccordionDetails>
                                    </Accordion>
                                </Box>
            }
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="secondary">
                    {strings.clinicalTrial.detail.close}
                </Button>
                <Button 
                    color="default" 
                    startIcon={<ImportExportIcon />}
                    onClick={exportPdf}
                    >
                    {strings.clinicalTrial.detail.export}
                </Button>
                <Button onClick={onSubmit} color="primary">
                    {submitLabel}
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default ClinicalTrialDetail;
