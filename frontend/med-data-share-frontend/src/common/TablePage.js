import React from 'react'
import CONFIG from "../config";

import strings from "../localization";
import Page from "./Page";

import MoreVert from '@material-ui/icons/MoreVert';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import VisibilityIcon from '@material-ui/icons/Visibility';
import AddIcon from '@material-ui/icons/Add';
import UndoIcon from '@material-ui/icons/Undo';

import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import {
    Button, IconButton,
    Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Drawer, LinearProgress,
    ListItemIcon,
    ListItemText,
    Menu,
    MenuItem,
    Table,
    TableBody,
    TableCell, TableFooter,
    TableHead, TablePagination,
    TableRow, TextField
} from "@material-ui/core";
import PageState from "../constants/PageState";
import DrawerWrapper from "./DrawerWrapper";

class TablePage extends Page {

    tableDescription = [];

    params = [
        { name: 'search', defaultValue: '' },
        { name: 'page', defaultValue: 1 },
        { name: 'perPage', defaultValue: 30 }
    ];

    deletedField = 'deleted';

    constructor(props) {
        super(props);

        this.state = {

            anchorEl: null,
            lockTable: false,
            ariaOwns: '',
            showConfirmDialog: false,
            itemForDelete: null,
            data: {},
            tableData: [],
            total: 0,
            selectedItem: undefined,
            pageState: PageState.View,
            showSearch: true,
            showAdd: true
        };

        this.props.changeFullScreen(false);

        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
        this.searchChanged = this.searchChanged.bind(this);
        this.onCancel = this.onCancel.bind(this);
        this.onFinish = this.onFinish.bind(this);
        this.cancelDelete = this.cancelDelete.bind(this);
        this.delete = this.delete.bind(this);
    }

    /** HANDLERS **/

    onCancel() {
        this.setPageState(PageState.View);
    }

    onFinish(item, fetch = true) {

        this.setState({
            selectedItem: item,
            pageState: PageState.View
        });

        if(!fetch) {
            return;
        }

        setTimeout(()=> {
            this.fetchData()
        }, 1000);
    }

    handleMenuClick(event, ariaOwns) {

        this.setState({
            anchorEl: event.currentTarget,
            ariaOwns: ariaOwns
        });
    }

    handleMenuClose() {
        this.setState({
            anchorEl: null,
            ariaOwns: ''
        });
    }

    handleChangePage(event, page) {

        this.state.searchData.page = page + 1;
        this.fetchData();
    }

    handleChangeRowsPerPage(event) {

        this.state.searchData.perPage = event.target.value;
        this.fetchData();
    }

    handleMenuView(item) {

    }

    handleMenuEdit(item) {

        this.state.selectedItem = item;
        this.handleMenuClose();

        this.setPageState(PageState.Edit);
    }

    handleMenuDelete(item) {

        this.setState({
            showConfirmDialog: true,
            selectedItem: item,
            anchorEl: null
        });
    }

    delete(item) {

    }

    handleRestore(item) {

        this.setState({
            selectedItem: item,
            anchorEl: null
        }, () => {
            this.restore(item);
        });
    }

    restore(item) {

    }

    cancelDelete() {
        this.setState({
            showConfirmDialog: false,
            selectedItem: null
        });
    }

    searchChanged(event) {

        let searchData = this.state.searchData;

        searchData[event.target.name] = event.target.value;

        this.setState({
            searchData: searchData
        });

        this.fetchData();
    }

    selectRow(item) {

        this.setState({
            selectedItem: item
        });
    }

    isRowSelected(item) {
        return this.state.selectedItem && item.id === this.state.selectedItem.id;
    }

    showDrawer() {

        return this.state.pageState !== PageState.View
    }

    setPageState(state) {

        this.setState({
            pageState: state
        });
    }

    /** RENDER TABLE  **/

    renderRowMenu(index, item) {

        let ariaOwns = 'action-menu-' + index;

        return(
            <TableCell>
                <IconButton
                    aria-owns={ this.state.anchorEl ? ariaOwns : undefined }
                    aria-haspopup="true"
                    onClick={ (event) => this.handleMenuClick(event, ariaOwns) }
                >
                    <MoreVert/>
                </IconButton>
                {
                    ariaOwns === this.state.ariaOwns &&
                    <Menu
                        id={ ariaOwns }
                        anchorEl={ this.state.anchorEl }
                        open={ Boolean(this.state.anchorEl) }
                        onClose={ () => this.handleMenuClose() }
                    >
                        <MenuItem onClick={ () => this.handleMenuView(item) }>
                            <ListItemIcon>
                                <VisibilityIcon/>
                            </ListItemIcon>
                            <ListItemText inset primary={ strings.table.view }/>
                        </MenuItem>
                        <MenuItem onClick={ () => this.handleMenuEdit(item) }>
                            <ListItemIcon>
                                <EditIcon/>
                            </ListItemIcon>
                            <ListItemText inset primary={ strings.table.edit }/>
                        </MenuItem>
                        {
                            !item[this.deletedField] &&
                            <MenuItem onClick={ () => this.handleMenuDelete(item) }>
                                <ListItemIcon>
                                    <DeleteIcon/>
                                </ListItemIcon>
                                <ListItemText inset primary={ strings.table.delete }/>
                            </MenuItem>
                        }
                        {
                            item[this.deletedField] &&
                            <MenuItem onClick={ () => this.handleRestore(item) }>
                                <ListItemIcon>
                                    <UndoIcon/>
                                </ListItemIcon>
                                <ListItemText inset primary={ strings.table.undo }/>
                            </MenuItem>
                        }

                    </Menu>
                }

            </TableCell>
        );
    }

    renderTableRowData(item) {

        let result = [];

        for(let description of this.tableDescription) {

            result.push(
                <TableCell key={ 'table-data-' + description.key + '-' + result.length }>
                    {
                        description.transform !== undefined &&
                        this[description.transform](item[description.key])
                    }
                    {
                        description.transform === undefined &&
                        item[description.key]
                    }
                </TableCell>
            );
        }

        return result;
    }

    renderTableRow(data = []) {

        let result = [];

        for(let item of data) {

            let className = 'table-row-' + result.length % 2;

            if(this.isRowSelected(item)) {
                className += ' table-row-selected';
            }

            result.push(
                <TableRow key={ 'table-row-' + result.length } className={ className } onClick={ () => this.selectRow(item) }>
                    { this.renderTableRowData(item) }
                    { this.renderRowMenu(result.length, item) }
                </TableRow>
            )
        }

        return (
            <TableBody>
                { result }
            </TableBody>
        );
    }

    renderTableHeader() {

        let result = [];

        for(let item of this.tableDescription) {

            result.push(
                <TableCell key={ 'table-header-' + result.length }>
                    { item.label }
                </TableCell>
            )
        }

        return (
            <TableHead className='table-header'>
                <TableRow>
                    { result }
                    <TableCell>
                        { strings.table.actions }
                    </TableCell>
                </TableRow>
            </TableHead>
        );
    }

    renderTableFooter() {

        return (
            <TableFooter>
                <TableRow>
                    <TablePagination count={ this.state.total } rowsPerPageOptions={ CONFIG.rowsPerPage }
                                     colSpan={ this.tableDescription.length + 1 } page={ this.state.searchData.page - 1 } rowsPerPage={ this.state.searchData.perPage }
                                     onChangePage={ this.handleChangePage } onChangeRowsPerPage={ this.handleChangeRowsPerPage }
                                     SelectProps={{
                                         native: true,
                                     }}
                    />
                </TableRow>
            </TableFooter>
        );
    }

    renderTable(data) {

        return <Table>

            {
                this.state.lockTable &&
                <div className='lock-table'>
                    <LinearProgress/>
                </div>
            }

            { this.renderTableHeader() }
            { this.renderTableRow(data) }
            { this.renderTableFooter() }
        </Table>
    }

    renderAddContent() {
        return ''
    }

    renderEditContent(item) {
        return ''
    }

    renderDrawerContent() {

        switch (this.state.pageState) {

            case PageState.Add: return this.renderAddContent();
            case PageState.Edit: return this.renderEditContent(this.state.selectedItem);

            default: return '';
        }
    }

    /** RENDER DIALOG **/

    renderDialog(title, text, cancel, deleteFunction) {

        return (
            <Dialog open={ this.state.showConfirmDialog }
                    onClose={ () => cancel() }
                    aria-labelledby='draggable-dialog-title'
            >
                <DialogTitle id='draggable-dialog-title'>
                    { title }
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        { text }
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={ () => cancel() } color='primary' variant="contained">
                        { strings.table.no }
                    </Button>
                    <Button onClick={ () => deleteFunction(this.state.selectedItem) } color='secondary' variant="contained">
                        { strings.table.yes }
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }

    getPageHeader() {
        return <h1>Title</h1>;
    }

    renderTableControls() {
        return [
            <IconButton key="0" onClick={ () => this.setPageState(PageState.Add) }>
                <AddIcon/>
            </IconButton>,
            
        ]
    }

    render() {

        return (
            <Grid id='table-page'>
                { this.renderDialog(strings.table.confirmDelete, 'To subscribe to this website, please enter your email address here. We will send\n' +
                    'updates occasionally.', this.cancelDelete, this.delete) }
                <div className='header'>
                    { this.getPageHeader() }

                    <div className='filter-controls'>

                        {
                            this.state.showSearch &&
                            <TextField
                                label={ strings.table.search }
                                type="search"
                                name='search'
                                value={ this.state.searchData.search }
                                onChange={ this.searchChanged }
                            />
                        }

                        {
                            this.state.showAdd &&
                            this.renderTableControls()
                        }
                    </div>
                </div>
                <Paper md={12}>
                    { this.renderTable(this.state.tableData) }
                </Paper>

                <Drawer id='drawer' anchor='right' open={  this.showDrawer() } onClose={ () => this.setPageState(PageState.View) } >
                    <DrawerWrapper onBack={ () => this.setPageState(PageState.View) }>
                        { this.renderDrawerContent() }
                    </DrawerWrapper>
                </Drawer>
            </Grid>
        );
    }
}

export default TablePage;