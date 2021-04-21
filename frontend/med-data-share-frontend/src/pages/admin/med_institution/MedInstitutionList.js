import React from 'react'
import TablePage from "../../../common/TablePage";

import {deleteUser, getMedInstitutions, restoreUser} from "../../../services/SuperAdminService";
import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import {withSnackbar} from "notistack";
import connect from "react-redux/es/connect/connect";

import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";

import {ListItemIcon, ListItemText, Menu, MenuItem, TableCell} from "@material-ui/core";

import IconButton from "@material-ui/core/IconButton";
import MoreVert from '@material-ui/icons/MoreVert';
import UndoIcon from '@material-ui/icons/Undo';
import DeleteIcon from '@material-ui/icons/Delete';

import AddMedInstitution from "./AddMedInstitution";

class MedInstitutionList extends TablePage {

    tableDescription = [
        { key: 'name', label: strings.medInstitutionList.name },
        { key: 'address', label: strings.medInstitutionList.address },
        // { key: 'dateCreated', label: strings.userList.dateCreated, transform: 'renderColumnDate' },
    ];

    fetchData() {

        this.setState({
            lockTable: true
        });

        getMedInstitutions()
        .then(response => {
            const medInstitutions = response.data;
            this.setState({
                tableData: medInstitutions,
                total: medInstitutions.length,
                lockTable: false
            });
        }).catch(error => {

        });

        
    }

    componentDidMount() {
        this.fetchData();
    }

    getPageHeader() {
        return <h1>{ strings.medInstitutionList.pageTitle }</h1>;
    }

    renderAddContent() {
        return <AddMedInstitution onCancel={ this.onCancel } onFinish={ this.onFinish }/>
    }

    delete(item) {

        this.setState({
            lockTable: true
        });

        deleteUser(item.id).then(response => {

            if(response && !response.ok) {
                this.onFinish(null);
                return;
            }

            this.props.enqueueSnackbar(strings.userList.userDelete, { variant: 'success' });

            this.onFinish(item);
            this.cancelDelete();

            this.setState({
                lockTable: false
            });
        });
    }

    restore(item) {

        this.setState({
            lockTable: true
        });

        restoreUser(item.id).then(response => {

            if(response && !response.ok) {
                this.onFinish(null);
                return;
            }

            this.props.enqueueSnackbar(strings.userList.userRestored, { variant: 'success' });

            this.onFinish(item);

            this.setState({
                lockTable: false
            });
        });
    }

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
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers })
{
    return { menu: menuReducers };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(MedInstitutionList)));