import Head from "next/head";
import React, { ReactNode, Fragment, useState, useEffect } from 'react';

import {
	Col,
	Row,
	Card,
	Container,
	Button
} from 'react-bootstrap';

import '@aws-amplify/ui-react/styles.css'
import Auth from '@aws-amplify/auth';
import useMounted from '../hooks/useMounted';
import { withAuthenticator } from "@aws-amplify/ui-react";
import { CognitoUserInterface } from '@aws-amplify/ui-components';

import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';

import historyService from '../services/historyService';

function History({user}: CognitoUserInterface) {

  const hasMounted = useMounted();
  const [accessToken, setAccessToken] = useState(null);
  const [history, setHistory] = useState([]);

  useEffect(() => {
    const accessToken = user.getSignInUserSession().getAccessToken().getJwtToken()
    setAccessToken(accessToken)

    historyService.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
    const response = historyService.get('/v1/history')
      .then(response => {
        const data = response.data.data
        setHistory(data)
      })
      .catch(e => {
        console.error(e)
      })
  }, [hasMounted, user]);

  const options = {
    // pageStartIndex: 0,
    sizePerPage: 5,
    hideSizePerPage: true,
    hidePageListOnlyOnePage: true
  };

  const columns = [
    {
      dataField: 'resource',
      text: 'Resource'
    },
    {
      dataField: 'eventType',
      text: 'Event Type'
    },
    {
      dataField: 'description',
      text: 'Data'
    }
  ];

	return (
        <Fragment>
          <Head>
            <title>Cenoa Bank | Accounts</title>
          </Head>

          <section className="py-10">
            <Container>
              <Row className="justify-content-center">
                <Col xs={12}>
                  <Card>
                    <Card.Body className="p-6">
                       <BootstrapTable
                         keyField="id"
                         data={ history }
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

export default withAuthenticator(History)
