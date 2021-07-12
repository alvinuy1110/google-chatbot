# google-chatbot

## Prerequisite

* JDK8
* Maven 3


## Client Libraries

### Java

* see https://cloud.google.com/dialogflow/es/docs/reference/libraries/java

## Use Cases

## Api vs Fulfillment

https://cloud.google.com/dialogflow/es/docs/api-overview

Fulfillment is optional and can be attached to intents.  It works by integrating via webhooks.
The webhook requirements are HTTPS and public url.  At this time, demo will a bit complex if we go this route.

### Fulfillment
![Fulfillment Flow](https://cloud.google.com/dialogflow/es/docs/images/fulfillment-flow.svg)


* fulfillmentText (String) Text to be pronounced to the user or shown on the screen.
* fulfillmentMessages (Object) Collection of rich messages to show the user.

### API

![API Flow](https://cloud.google.com/dialogflow/es/docs/images/api-flow.svg)

## Demo

* Integration via Demo Widget

** https://bot.dialogflow.com/cfd25ee3-3aa6-440d-a652-9a57f3a13ff5
** Customizations like name, description and icon is handled in the Agent settings

* Integration with iFrame

** this spring boot app serves HTML with embedded iframe
** http://localhost:8080
** Customizations like name, description and icon is handled in the Agent settings


## Inputs

Text and audio both works.  However may need tweaking if inside a VM.

## Locale

Locale settings in the Agent config.  English for now.

## More

* Training by capturing actual inputs and correcting in the future
* Logging



## Steps

* setup spring boot
* setup google dialogflow libraries
* setup credential keys
* setup firewall proxy if needed

## Authentication

see https://cloud.google.com/dialogflow/es/docs/quick/setup

### Create Service Account

Go the said Google Project.  Read https://cloud.google.com/dialogflow/es/docs/quick/setup#sa-create for more details.

#### Assign Roles

Some of the roles https://cloud.google.com/dialogflow/es/docs/access-control
* see https://cloud.google.com/iam/docs/understanding-roles#dialogflow-roles

For now we use "Dialogflow API Client"

### Generate Key

This is the private key (credentials) to use for accessing the API via said account


### Credential
Save the credential JSON info.
Link the credential file to your app.

Set the environment variable GOOGLE_APPLICATION_CREDENTIALS
Example:
export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"

## Dialogflow v2

https://cloud.google.com/dialogflow/es/docs/reference/rest/v2-overview


### Getting Started
https://cloud.google.com/dialogflow/es/docs/quick



### Editions

https://cloud.google.com/dialogflow/docs/editions

We will be using the Trial Edition with the ES Agent.  This should have basic functionality and is sufficient for prototyping for now.


## Build the agent

https://cloud.google.com/dialogflow/es/docs/quick/build-agent

## System Entities

https://cloud.google.com/dialogflow/es/docs/reference/system-entities



## Small Talk

Allow to have human like conversation with chatbot



## Knowledge base (beta)

https://cloud.google.com/dialogflow/es/docs/knowledge-connectors
limitations on language support...https://cloud.google.com/dialogflow/es/docs/reference/language

### FAQ style

Import say a CSV of question-answer that will be augmented to the Intents

## How-Tos

* https://cloud.google.com/dialogflow/es/docs/how


## Troubleshooting

1. Error: NoRouteHostException

If behind a corporate firewall, ensure to pass in the java properties in env variable

```
-Dhttps.proxyHost=<host> -Dhttps.proxyPort=<port>
```



TODO
======

* JSON conversion??  -- use GSON instead
* logging at sdk level..


