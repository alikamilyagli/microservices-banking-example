// import node module libraries
import React, { Fragment } from 'react';
import Link from 'next/link';
import { Row, Col, Container } from 'react-bootstrap';

const FooterWrapper = () => {
	return (
    <Fragment>
			<div className={`footer secondary fixed-bottom`}>
				<Container fluid>
					<Row className="align-items-center g-0 border-top py-2">
						{/* Desc */}
						<Col md={6} sm={12} className="text-center text-md-start">
							<span>{(new Date()).getFullYear()} © cenoa.com</span>
						</Col>
						{/* Links */}
						<Col md={6} sm={12}>
							<nav className="nav nav-footer justify-content-center justify-content-md-end">
								<Link href="/" className="nav-link active ps-0">
									Privacy
								</Link>
								<Link href="/" className="nav-link">
                  Contact
                </Link>
							</nav>
						</Col>
					</Row>
				</Container>
			</div>
		</Fragment>
    );
};


export default FooterWrapper;
