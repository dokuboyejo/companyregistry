-- Table: public.company_beneficiaries

-- DROP TABLE public.company_beneficiaries;

CREATE TABLE public.company_beneficiaries
(
  "companyId" bigint NOT NULL,
  "beneficiaryId" bigint NOT NULL,
  CONSTRAINT "pk_companyId_beneficiaryId" PRIMARY KEY ("companyId", "beneficiaryId"),
  CONSTRAINT fk_beneficiary_id FOREIGN KEY ("beneficiaryId")
      REFERENCES public.beneficiary (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_company_id FOREIGN KEY ("companyId")
      REFERENCES public.company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.company_beneficiaries
  OWNER TO postgres;

-- Index: public.fki_beneficiary_id

-- DROP INDEX public.fki_beneficiary_id;

CREATE INDEX fki_beneficiary_id
  ON public.company_beneficiaries
  USING btree
  ("beneficiaryId");

