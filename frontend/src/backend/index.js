import {init} from './appFetch';
import * as catalogService from './catalogService';
import * as userService from './userService';
import * as bidService from './bidService';

export {default as NetworkError} from "./NetworkError";

export default {init, userService, catalogService, bidService};
