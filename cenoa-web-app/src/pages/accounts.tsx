import Head from "next/head";
import React, { ReactNode, Fragment, useState, useEffect } from 'react';
import { v4 } from "uuid";

import {
	Col,
	Row,
	Card,
	Container,
	Button
} from 'react-bootstrap';

import AccountModal from '../components/AccountModal'
import DepositModal from '../components/DepositModal'
import WithdrawModal from '../components/WithdrawModal'

import '@aws-amplify/ui-react/styles.css'
import Auth from '@aws-amplify/auth';

import useMounted from '../hooks/useMounted';
import { withAuthenticator } from "@aws-amplify/ui-react";
import { CognitoUserInterface } from '@aws-amplify/ui-components';

import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';

import accountService from '../services/accountService';

type AccountType = {
  balance: number;
  currency: string;
  description: string;
  id: number;
  status: string;
  uuid: string;
};

function Accounts({user}: CognitoUserInterface) {

  const hasMounted = useMounted();
  const [accessToken, setAccessToken] = useState(null);
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState<string>("");
  const [timer, setTimer] = useState("")

  const [showAccountModal, setShowAccountModal] = useState(false);
  const [showDepositModal, setShowDepositModal] = useState(false);
  const [showWithdrawModal, setShowWithdrawModal] = useState(false);

  useEffect(() => {
    const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
    setAccessToken(accessToken)

    accountService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
    accountService.get('/v1/account')
      .then(response => {
        const data = response.data.data

        //çakallık
        for(var i = 0; i < data.length; i++){
         let uuid = data[i].uuid
         data[i].action = (
            <Fragment>
                <div className="text-center">
                  <Button
                    className="btn btn-sm btn-secondary me-2"
                    onClick={() => editAccount(uuid)}
                  >
                    Edit
                  </Button>
                  <Button
                    className="btn btn-sm btn-primary me-2"
                    onClick={() => depositAccount(uuid)}
                  >
                    Deposit
                  </Button>
                  <Button
                    className="btn btn-sm btn-primary mt-2"
                    onClick={() => withdrawAccount(uuid)}
                  >
                    Withdraw
                  </Button>
                </div>
            </Fragment>
         )
        }
        setAccounts(data)
      })
      .catch(e => {
        console.error(e)
      })
  }, [hasMounted, showAccountModal, showDepositModal, showWithdrawModal, user, timer]);

  useEffect(() => {
    setTimeout(() => {
      setTimer(v4())
    }, 1000);
  }, [showWithdrawModal, showDepositModal])

  const options = {
    // pageStartIndex: 0,
    sizePerPage: 5,
    hideSizePerPage: true,
    hidePageListOnlyOnePage: true
  };

  const addAccount = () => {
    setSelectedAccount("")
    setShowAccountModal(true)
  }

  const editAccount = (accountUuid:string) => {
    setSelectedAccount(accountUuid)
    setShowAccountModal(true)
  }

  const depositAccount = (accountUuid:string) => {
    setSelectedAccount(accountUuid)
    setShowDepositModal(true)
  }

  const withdrawAccount = (accountUuid:string) => {
    setSelectedAccount(accountUuid)
    setShowWithdrawModal(true)
  }

  const columns = [
    {
      dataField: 'id',
      text: 'Account ID'
    },
    {
      dataField: 'balance',
      text: 'Balance'
    },
    {
      dataField: 'currency',
      text: 'Currency'
    },
    {
      dataField: 'status',
      text: "Status"
    },
    {
      dataField: 'action',
      text: "Actions"
    }
  ];

	return (
        <Fragment>
          <Head>
            <title>Cenoa Bank | Accounts</title>
          </Head>

          <section className="py-6 mb-12">
            <Container>
              <AccountModal
                user={user}
                selectedAccount={selectedAccount}
                show={showAccountModal}
                setShowAccountModal={setShowAccountModal}
                onHide={() => setShowAccountModal(false)}
              />
              <DepositModal
                user={user}
                selectedAccount={selectedAccount}
                show={showDepositModal}
                setShowDepositModal={setShowDepositModal}
                onHide={() => setShowDepositModal(false)}
              />
              <WithdrawModal
                user={user}
                selectedAccount={selectedAccount}
                show={showWithdrawModal}
                setShowWithdrawModal={setShowWithdrawModal}
                onHide={() => setShowWithdrawModal(false)}
              />
              <Row className="text-end mb-4">
                <Col xs={12}>
                  <Button className="btn btn-secondary" onClick={ () => addAccount() }>
                    Add Account
                  </Button>
                </Col>
              </Row>
              <Row className="justify-content-center">
                <Col xs={12}>
                  <Card>
                    <Card.Body className="p-6">
                       <BootstrapTable
                         keyField="id"
                         data={ accounts }
                         columns={ columns }
                         pagination={ paginationFactory(options) }
                       />
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            </Container>
          </section>

        </Fragment>
	);
}

export default withAuthenticator(Accounts)
